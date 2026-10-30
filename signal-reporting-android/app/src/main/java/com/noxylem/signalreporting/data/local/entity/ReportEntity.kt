package com.noxylem.signalreporting.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reports")
data class ReportEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val techId: String,
    val customerCompany: String? = null,
    val siteAddress: String? = null,
    val siteContact: String? = null,
    val siteContactPhone: String? = null,
    val reportDateEpoch: Long? = null,
    val status: ReportStatus = ReportStatus.DRAFT,
    val signed: Boolean = false,
    val reasonForScanServices: String? = null,
    val additionalCustomerRequirements: String? = null,
    val servicesPerformed: String? = null,
    val surfacesWorkedOn: String? = null,
    val recommendations: String? = null,
    val limitations: String? = null,
    val servicesNarrative: String? = null,
    val technicianName: String? = null,
    val loginTimeEpoch: Long? = null,
    val onSiteTimeEpoch: Long? = null,
    val completionTimeEpoch: Long? = null,
    val logOffTimeEpoch: Long? = null,
    val createdAt: Long,
    val updatedAt: Long,
)
