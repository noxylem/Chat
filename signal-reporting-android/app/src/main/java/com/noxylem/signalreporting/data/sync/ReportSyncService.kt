package com.noxylem.signalreporting.data.sync

/**
 * Future server sync entry point. Not wired in the scaffold — local Room is the source of truth.
 */
interface ReportSyncService {
    suspend fun syncPendingReports(): SyncResult

    suspend fun syncPendingImageSets(): SyncResult
}

data class SyncResult(
    val success: Boolean,
    val message: String,
)

/** No-op implementation until a backend exists. */
object StubReportSyncService : ReportSyncService {
    override suspend fun syncPendingReports(): SyncResult =
        SyncResult(success = false, message = "Sync not implemented — data stays on device.")

    override suspend fun syncPendingImageSets(): SyncResult =
        SyncResult(success = false, message = "Sync not implemented — data stays on device.")
}
