package com.noxylem.signalreporting.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "image_sets",
    foreignKeys = [
        ForeignKey(
            entity = ReportEntity::class,
            parentColumns = ["id"],
            childColumns = ["reportId"],
            onDelete = ForeignKey.SET_NULL,
        ),
    ],
    indices = [Index("reportId")],
)
data class ImageSetEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val reportId: Long? = null,
    val category: ImageSetCategory = ImageSetCategory.UNASSIGNED,
    val title: String,
    val createdAt: Long,
)
