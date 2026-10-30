package com.noxylem.signalreporting.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "image_assets",
    foreignKeys = [
        ForeignKey(
            entity = ImageSetEntity::class,
            parentColumns = ["id"],
            childColumns = ["imageSetId"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [Index("imageSetId")],
)
data class ImageAssetEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val imageSetId: Long,
    val localFilePath: String? = null,
    val contentUri: String? = null,
    val name: String,
    val sortOrder: Int = 0,
    val notes: String? = null,
    val surfaceType: String? = null,
    val revisionOfId: Long? = null,
)
