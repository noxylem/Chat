package com.noxylem.signalreporting.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.noxylem.signalreporting.data.local.dao.ImageAssetDao
import com.noxylem.signalreporting.data.local.dao.ImageSetDao
import com.noxylem.signalreporting.data.local.dao.ReportDao
import com.noxylem.signalreporting.data.local.entity.ImageAssetEntity
import com.noxylem.signalreporting.data.local.entity.ImageSetEntity
import com.noxylem.signalreporting.data.local.entity.ReportEntity

@Database(
    entities = [
        ReportEntity::class,
        ImageSetEntity::class,
        ImageAssetEntity::class,
    ],
    version = 1,
    exportSchema = false,
)
@TypeConverters(Converters::class)
abstract class SignalDatabase : RoomDatabase() {
    abstract fun reportDao(): ReportDao
    abstract fun imageSetDao(): ImageSetDao
    abstract fun imageAssetDao(): ImageAssetDao

    companion object {
        private const val DB_NAME = "signal_reporting.db"

        fun create(context: Context): SignalDatabase =
            Room.databaseBuilder(context.applicationContext, SignalDatabase::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
                .build()
    }
}
