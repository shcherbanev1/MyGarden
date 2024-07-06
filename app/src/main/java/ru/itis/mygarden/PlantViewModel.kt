package ru.itis.mygarden

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PlantViewModel(private val plantDao: PlantDao) : ViewModel() {

    suspend fun getAllPlants(): List<Plant> {
        return withContext(Dispatchers.IO) {
            plantDao.getAllPlants()
        }
    }

    fun addPlant(plant: Plant) {
        viewModelScope.launch(Dispatchers.IO) {
            plantDao.insertPlant(plant)
        }
    }

    fun updatePlant(plant: Plant) {
        viewModelScope.launch(Dispatchers.IO) {
            plantDao.updatePlant(plant)
        }
    }

    fun deletePlant(plant: Plant) {
        viewModelScope.launch(Dispatchers.IO) {
            plantDao.deletePlant(plant)
        }
    }
}
