package ru.itis.mygarden.data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update


@Dao
interface UserDao {

    @Query(
        "select * from user where id = :id limit 1"
    )
    suspend fun getUser(id : Long) : User

    @Update
    suspend fun updateUser(user : User)

}
