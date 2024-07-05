package ru.itis.mygarden

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "plants")
class Plant(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "isWatered")
    var isWatered: Boolean,
    @ColumnInfo(name = "imgSource")
    var imgSource: String,
    @ColumnInfo(name = "sunlight")
    var sunlight: String
) {

}
