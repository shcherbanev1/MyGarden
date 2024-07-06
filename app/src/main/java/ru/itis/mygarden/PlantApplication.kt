package ru.itis.mygarden

import android.app.Application
import ru.itis.mygarden.data.PlantDatabase
import ru.itis.mygarden.data.PlantViewModel

class PlantApplication: Application() {
    val database by lazy { PlantDatabase.getDataBase(this) }
    val viewModel by lazy { PlantViewModel(database.plantDao()) }
}