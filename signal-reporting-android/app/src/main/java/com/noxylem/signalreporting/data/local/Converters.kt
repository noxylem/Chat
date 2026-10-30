package com.noxylem.signalreporting.data.local

import androidx.room.TypeConverter
import com.noxylem.signalreporting.data.local.entity.ImageSetCategory
import com.noxylem.signalreporting.data.local.entity.ReportStatus

class Converters {
    @TypeConverter
    fun fromReportStatus(value: ReportStatus): String = value.name

    @TypeConverter
    fun toReportStatus(value: String): ReportStatus = ReportStatus.valueOf(value)

    @TypeConverter
    fun fromImageSetCategory(value: ImageSetCategory): String = value.name

    @TypeConverter
    fun toImageSetCategory(value: String): ImageSetCategory = ImageSetCategory.valueOf(value)
}
