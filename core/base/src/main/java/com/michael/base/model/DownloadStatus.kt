package com.michael.base.model

data class DownloadStatus(
    val id: Long,
    val isSuccess: Boolean,
    val path: String?,
)
