package ru.itis.mygarden

import android.app.Application

class PlantApplication: Application() {
    private val database by lazy { PlantDatabase.getDataBase(this) }
    val repository by lazy { PlantRepository(database.plantDao()) }
}