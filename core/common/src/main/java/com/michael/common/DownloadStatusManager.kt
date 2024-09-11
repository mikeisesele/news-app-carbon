package com.michael.common

import com.michael.base.model.DownloadStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DownloadStatusManager @Inject constructor() {
    private val statuses = mutableMapOf<Long, DownloadStatus>()

    private val _statusEvents =
        MutableSharedFlow<DownloadStatus>(extraBufferCapacity = Int.MAX_VALUE)

    fun getCompletionEvents(id: Long): Flow<DownloadStatus> {
        return _statusEvents.asSharedFlow().filter { it.id == id }.run {
            // Ensure we lookup existing map key and emit any saved events
            // to avoid any race conditions
            if (statuses.containsKey(id)) {
                onStart { emit(statuses.getValue(id)) }
            } else {
                this
            }
        }
    }

    fun onDownloadCompleted(status: DownloadStatus) {
        statuses[status.id] = status
        _statusEvents.tryEmit(status)
    }
}
