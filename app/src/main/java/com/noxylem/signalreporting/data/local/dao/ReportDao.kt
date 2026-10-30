package com.noxylem.signalreporting.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.noxylem.signalreporting.data.local.entity.ReportEntity
import com.noxylem.signalreporting.data.local.entity.ReportStatus
import kotlinx.coroutines.flow.Flow

@Dao
interface ReportDao {
    @Query("SELECT * FROM reports ORDER BY updatedAt DESC")
    fun observeAll(): Flow<List<ReportEntity>>

    @Query("SELECT * FROM reports WHERE id = :id")
    fun observeById(id: Long): Flow<ReportEntity?>

    @Query("SELECT * FROM reports WHERE id = :id")
    suspend fun getById(id: Long): ReportEntity?

    @Query("SELECT * FROM reports WHERE status = :status ORDER BY updatedAt DESC")
    fun observeByStatus(status: ReportStatus): Flow<List<ReportEntity>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(report: ReportEntity): Long

    @Update
    suspend fun update(report: ReportEntity)
}
