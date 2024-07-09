package ru.itis.mygarden.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(
    tableName = "user"
)
data class User(
    @PrimaryKey(autoGenerate = false)
    val id : Long,
    val name : String,
    @ColumnInfo("image_path")
    val imagePath : String
)