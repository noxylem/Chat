package com.noxylem.signalreporting.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.noxylem.signalreporting.data.local.entity.ImageSetCategory
import com.noxylem.signalreporting.data.local.entity.ImageSetEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ImageSetDao {
    @Query("SELECT * FROM image_sets ORDER BY createdAt DESC")
    fun observeAll(): Flow<List<ImageSetEntity>>

    @Query("SELECT * FROM image_sets WHERE id = :id")
    fun observeById(id: Long): Flow<ImageSetEntity?>

    @Query("SELECT * FROM image_sets WHERE reportId IS NULL ORDER BY createdAt DESC")
    fun observeUnassigned(): Flow<List<ImageSetEntity>>

    @Query("SELECT * FROM image_sets WHERE reportId = :reportId ORDER BY createdAt ASC")
    fun observeForReport(reportId: Long): Flow<List<ImageSetEntity>>

    @Query("SELECT * FROM image_sets WHERE category = :category ORDER BY createdAt DESC")
    fun observeByCategory(category: ImageSetCategory): Flow<List<ImageSetEntity>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(imageSet: ImageSetEntity): Long

    @Update
    suspend fun update(imageSet: ImageSetEntity)
}
