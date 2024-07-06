package ru.itis.mygarden.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface PlantDao {

    @Query("SELECT * FROM plants ORDER BY id ASC")
    suspend fun getAllPlants(): List<Plant>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlant(plant: Plant)

    @Update
    suspend fun updatePlant(plant: Plant)

    @Delete
    suspend fun deletePlant(plant: Plant)

    @Query("SELECT * FROM plants WHERE id = :id")
    suspend fun getPlantById(id: Int): Plant?

    @Query("SELECT * FROM plants WHERE name = :name")
    suspend fun getPlantByName(name: String): Plant?

}