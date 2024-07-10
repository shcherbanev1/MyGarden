package ru.itis.mygarden.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "plants")
data class Plant(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "description")
    var description: String?,

    @ColumnInfo(name = "light_preference")
    var sunlight: String?,

    @ColumnInfo(name = "next_watering_time")
    var nextWateringTime: Long?,

    @ColumnInfo(name = "watering_frequency")
    val wateringFrequency: Int,

    @ColumnInfo(name = "img_source")
    var imgSource: String?,
)
