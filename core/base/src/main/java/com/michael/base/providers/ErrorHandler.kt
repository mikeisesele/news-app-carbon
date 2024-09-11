package com.michael.base.providers

interface ErrorHandler {
    fun getErrorMessage(error: Throwable): String
}
