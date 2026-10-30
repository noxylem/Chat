package com.noxylem.signalreporting.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.noxylem.signalreporting.data.local.entity.ImageAssetEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ImageAssetDao {
    @Query("SELECT * FROM image_assets WHERE imageSetId = :imageSetId ORDER BY sortOrder ASC, id ASC")
    fun observeForImageSet(imageSetId: Long): Flow<List<ImageAssetEntity>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(asset: ImageAssetEntity): Long

    @Update
    suspend fun update(asset: ImageAssetEntity)

    @Query("DELETE FROM image_assets WHERE id = :id")
    suspend fun deleteById(id: Long)
}
