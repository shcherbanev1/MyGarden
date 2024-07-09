package ru.itis.mygarden.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.itis.mygarden.data.Plant
import ru.itis.mygarden.data.PlantDao
import ru.itis.mygarden.data.PlantDatabase
import java.lang.ref.WeakReference
import ru.itis.mygarden.data.api.ApiPlantInfoHandler
import ru.itis.mygarden.exception.PlantNotFoundException

class PlantViewModel(context: Context) : ViewModel() {

    private val contextRef: WeakReference<Context> = WeakReference(context)

    private val plantDao: PlantDao = contextRef.get()?.let {
        PlantDatabase.getDataBase(it).plantDao()
    } ?: throw IllegalStateException("Context is null")

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

    suspend fun addPlantFromApi(plantName: String): Boolean {
            return try {
                val handler = ApiPlantInfoHandler(plantName)
                addPlant(handler.fetchPlantFromJson())
                true
            } catch (e: PlantNotFoundException) {
                false
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
