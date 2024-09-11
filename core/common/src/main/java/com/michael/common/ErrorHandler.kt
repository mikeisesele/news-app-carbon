package com.michael.common

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import com.michael.base.providers.ErrorHandler
import com.michael.base.providers.StringProvider
import javax.inject.Inject
class ErrorHandler @Inject constructor(
    private val stringProvider: StringProvider,
) : ErrorHandler {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun getErrorMessage(error: Throwable): String {
        return when (error) {
            is HttpException -> "Network failed"
            else -> "Something Went Wrong"
        }
    }
}
