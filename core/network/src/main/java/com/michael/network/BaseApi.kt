package com.michael.network

import com.michael.network.exceptions.GeneralApiException
import com.michael.network.model.RetrofitApiError
import com.squareup.moshi.JsonAdapter
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.HttpURLConnection.HTTP_BAD_GATEWAY
import java.net.HttpURLConnection.HTTP_BAD_REQUEST
import java.net.HttpURLConnection.HTTP_CLIENT_TIMEOUT
import java.net.HttpURLConnection.HTTP_FORBIDDEN
import java.net.HttpURLConnection.HTTP_NOT_FOUND
import java.net.HttpURLConnection.HTTP_UNAUTHORIZED
import javax.inject.Inject
import javax.inject.Singleton

/**
 * This class is used as a wrapper around a [Response] which adds additional functionality that is
 * needed, such as analytics event logging via [EventLogger].
 * This is used as part of the REST [RefreshTokenApi], and can be used with further REST apis.
 * After a call has been made we appropriately map an [ApiResult.Success] or [ApiResult.Failure],
 * and if needed log an analytics event.
 *
 * @param eventLogger an implementation of an [EventLogger] that logs [AnalyticsEvent]s
 * @param adapter a [JsonAdapter] which parses errors returned into a [RetrofitApiError]
 */
@Suppress("TooGenericExceptionCaught")
@Singleton
class BaseApi @Inject constructor(
    private val adapter: JsonAdapter<RetrofitApiError>,
) {
    fun <T : Any> asResult(response: Response<T>): ApiResult<T> {
        return try {
            return apiResult(response)
        } catch (e: Exception) {
            when (e) {
                is IOException -> ApiResult.NetworkFailure(e) // Network error
                is HttpException -> { // Unexpected non-2xx error
                    e.response()?.let {
                        ApiResult.NetworkFailure(e)
                    } ?: ApiResult.NetworkFailure(e)
                }

                else -> ApiResult.NetworkFailure(e) // Unknown Error
            }
        }
    }

    private fun <T : Any> apiResult(response: Response<T>): ApiResult<T> {
        return when {
            response.isSuccessful && response.body() != null -> ApiResult.Success(response.body()!!)
            else -> {
                val errorMessage = parseErrorMessage(response)
                val failureMessage = when (response.code()) {
                    HTTP_UNAUTHORIZED -> "Unauthorized"
                    HTTP_BAD_REQUEST -> "Bad request"
                    HTTP_FORBIDDEN -> "Forbidden"
                    HTTP_BAD_GATEWAY -> "Bad gateway"
                    HTTP_CLIENT_TIMEOUT -> "Client timeout"
                    HTTP_NOT_FOUND -> "Not Found"
                    else -> GeneralApiException(errorMessage).message
                }
                ApiResult.Failure(response.code(), failureMessage)
            }
        }
    }

    private fun <T> parseErrorMessage(response: Response<T>): String {
        val errorJson = response.errorBody()?.string().orEmpty()
        val apiError = try {
            adapter.fromJson(errorJson)
        } catch (e: Exception) {
            null
        }
        return apiError?.error ?: errorJson
    }
}
