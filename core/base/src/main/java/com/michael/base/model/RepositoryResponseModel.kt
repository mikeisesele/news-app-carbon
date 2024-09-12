package com.michael.base.model

// used to return data to the viewmodel in cases where the api call is successful
// but the data is null and there is an error message to display.
// so in this case we do not have a thrown exception
data class RepositoryResponseModel<T>(
    val data: T? = null,
    val apiErrorMessage: String? = null
)