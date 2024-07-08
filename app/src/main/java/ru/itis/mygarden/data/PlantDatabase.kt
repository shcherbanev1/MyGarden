package ru.itis.mygarden.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Plant::class, User::class], version = 1, exportSchema = false)
abstract class PlantDatabase: RoomDatabase() {
    abstract fun plantDao(): PlantDao

    abstract fun userDao() : UserDao

    companion object {
        @Volatile
        private var INSTANCE: PlantDatabase? = null

        private val firstRunCallback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                db.execSQL("insert into user (id, name, image_path) values (1, '', '')")
            }
        }

        fun getDataBase(context: Context): PlantDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PlantDatabase::class.java,
                    name = "plant_database"
                )
                    .addCallback(callback = firstRunCallback)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}