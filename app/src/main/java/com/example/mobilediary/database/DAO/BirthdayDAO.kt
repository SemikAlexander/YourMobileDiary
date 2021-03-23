package com.example.mobilediary.database.DAO

import androidx.room.*
import com.example.mobilediary.database.Birthday

@Dao
interface BirthdayDAO {
    @Query("SELECT * FROM user_birthdays")
    fun getAllEvents(): List<Birthday>

    @Insert
    fun insertEvent(vararg birthday: Birthday)

    @Update
    fun updateEvent(birthday: Birthday)

    @Delete
    fun deleteEvent(vararg birthday: Birthday)
}