package ru.itis.mygarden.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.itis.mygarden.data.Plant
import ru.itis.mygarden.data.PlantDao
import ru.itis.mygarden.data.PlantDatabase
import ru.itis.mygarden.data.User
import ru.itis.mygarden.data.UserDao
import java.lang.ref.WeakReference
import ru.itis.mygarden.data.api.ApiPlantInfoHandler
import ru.itis.mygarden.exception.PlantNotFoundException
import ru.itis.mygarden.util.Translator
import ru.itis.mygarden.util.TranslatorENtoRU

class PlantViewModel(context: Context) : ViewModel() {

    private val contextRef: WeakReference<Context> = WeakReference(context)

    private val plantDao: PlantDao = contextRef.get()?.let {
        PlantDatabase.getDataBase(it).plantDao()
    } ?: throw IllegalStateException("Context is null")

    private val userDao : UserDao = contextRef.get()?.let {
        PlantDatabase.getDataBase(it).userDao()
    } ?: throw IllegalStateException("Context is null")

    private val _userStateFlow = MutableStateFlow<User?>(null)
    val userStateFlow : StateFlow<User?>
        get() = _userStateFlow

    fun getUser() {
        viewModelScope.launch {
            _userStateFlow.emit(userDao.getUser(1))
        }
    }

    fun updateUser(name : String, imagePath : String) {
        viewModelScope.launch {
            userStateFlow.value?.let {
                return@launch userDao.updateUser(it.copy(name = name, imagePath = imagePath))
            }
            userDao.updateUser(User(
                id = 1,
                name = name,
                imagePath = imagePath
            ))
        }
    }

    suspend fun getAllPlants(): List<Plant> {
        return withContext(Dispatchers.IO) {
            plantDao.getAllPlants()
        }
    }

    fun addPlant(plant: Plant) {
        viewModelScope.launch(Dispatchers.IO) {
            val translator = TranslatorENtoRU(viewModelScope)

            val deferredNameTranslation = CompletableDeferred<String>()
            val deferredDescriptionTranslation = CompletableDeferred<String>()

            translator.translate(plant.name) { translatedText ->
                deferredNameTranslation.complete(translatedText)
            }
            plant.description?.let {
                translator.translate(it) { translatedText ->
                    deferredDescriptionTranslation.complete(translatedText)
                }
            }

            // Await all translations
            val translatedName = deferredNameTranslation.await()
            val translatedDescription = deferredDescriptionTranslation.await()

            // Update plant object with translated values
            plant.name = "  ${translatedName.replaceFirstChar { it.uppercaseChar() }}  "
            plant.description = translatedDescription
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

    fun updateNextWateringTime(plant: Plant) {
        plant.nextWateringTime = System.currentTimeMillis() + plant.wateringFrequency * 86400 * 1000
    }

}
