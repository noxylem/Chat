package com.noxylem.signalreporting.data.repository

import com.noxylem.signalreporting.data.local.dao.ImageAssetDao
import com.noxylem.signalreporting.data.local.dao.ImageSetDao
import com.noxylem.signalreporting.data.local.dao.ReportDao
import com.noxylem.signalreporting.data.local.entity.ImageAssetEntity
import com.noxylem.signalreporting.data.local.entity.ImageSetCategory
import com.noxylem.signalreporting.data.local.entity.ImageSetEntity
import com.noxylem.signalreporting.data.local.entity.ReportEntity
import com.noxylem.signalreporting.data.local.entity.ReportStatus
import kotlinx.coroutines.flow.Flow

/**
 * Offline-first repository: all reads and writes go to Room on device.
 * Server sync is not implemented; see [com.noxylem.signalreporting.data.sync.ReportSyncService].
 */
class LocalReportRepository(
    private val reportDao: ReportDao,
    private val imageSetDao: ImageSetDao,
    private val imageAssetDao: ImageAssetDao,
) {
    fun observeReports(): Flow<List<ReportEntity>> = reportDao.observeAll()

    fun observeReport(reportId: Long): Flow<ReportEntity?> = reportDao.observeById(reportId)

    fun observeReportsByStatus(status: ReportStatus): Flow<List<ReportEntity>> =
        reportDao.observeByStatus(status)

    fun observeImageSets(): Flow<List<ImageSetEntity>> = imageSetDao.observeAll()

    fun observeImageSet(imageSetId: Long): Flow<ImageSetEntity?> = imageSetDao.observeById(imageSetId)

    fun observeUnassignedImageSets(): Flow<List<ImageSetEntity>> = imageSetDao.observeUnassigned()

    fun observeImageSetsForReport(reportId: Long): Flow<List<ImageSetEntity>> =
        imageSetDao.observeForReport(reportId)

    fun observeImageAssets(imageSetId: Long): Flow<List<ImageAssetEntity>> =
        imageAssetDao.observeForImageSet(imageSetId)

    suspend fun getReport(reportId: Long): ReportEntity? = reportDao.getById(reportId)

    suspend fun createDraftReport(techId: String): Long {
        val now = System.currentTimeMillis()
        return reportDao.insert(
            ReportEntity(
                techId = techId,
                status = ReportStatus.DRAFT,
                createdAt = now,
                updatedAt = now,
            ),
        )
    }

    suspend fun updateReport(report: ReportEntity) {
        reportDao.update(report.copy(updatedAt = System.currentTimeMillis()))
    }

    suspend fun createImageSet(
        title: String,
        reportId: Long? = null,
        category: ImageSetCategory = when {
            reportId != null -> ImageSetCategory.ATTACHED
            else -> ImageSetCategory.UNASSIGNED
        },
    ): Long {
        val now = System.currentTimeMillis()
        return imageSetDao.insert(
            ImageSetEntity(
                reportId = reportId,
                category = category,
                title = title,
                createdAt = now,
            ),
        )
    }

    suspend fun updateImageSet(imageSet: ImageSetEntity) {
        imageSetDao.update(imageSet)
    }

    suspend fun addImageAsset(asset: ImageAssetEntity): Long = imageAssetDao.insert(asset)

    suspend fun updateImageAsset(asset: ImageAssetEntity) {
        imageAssetDao.update(asset)
    }
}
