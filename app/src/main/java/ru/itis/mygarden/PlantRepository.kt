package ru.itis.mygarden

import androidx.annotation.WorkerThread

class PlantRepository(private val plantDao: PlantDao) {
    val allPlant: List<Plant> = plantDao.allPlants()

    @WorkerThread
    suspend fun insertPlant(plant: Plant) {
        plantDao.insertPlant(plant)
    }

    @WorkerThread
    suspend fun updatePlant(plant: Plant) {
        plantDao.updatePlant(plant)
    }
}