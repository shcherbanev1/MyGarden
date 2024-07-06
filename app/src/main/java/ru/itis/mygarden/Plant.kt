package ru.itis.mygarden

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "plants")
data class Plant(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "description")
    val description: String?,

    @ColumnInfo(name = "light_preference")
    val sunlight: String?,

    @ColumnInfo(name = "next_watering_time")
    var nextWateringTime: Long?,

    @ColumnInfo(name = "watering_frequency")
    val wateringFrequency: Int,

    @ColumnInfo(name = "img_source")
    var imgSource: String?,
)
