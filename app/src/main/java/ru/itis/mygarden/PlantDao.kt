package ru.itis.mygarden

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PlantDao {
    @Query("SELECT * FROM plants ORDER BY id ASC")
    fun allPlants(): List<Plant>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlant(plant: Plant)

    suspend fun updatePlant(plant: Plant)

    suspend fun deletePlant(plant: Plant)

}