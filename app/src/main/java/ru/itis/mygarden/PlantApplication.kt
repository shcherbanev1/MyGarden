package ru.itis.mygarden

import android.app.Application

class PlantApplication: Application() {
    val database by lazy { PlantDatabase.getDataBase(this) }
    val viewModel by lazy { PlantViewModel(database.plantDao()) }
}