package com.example.mobilediary.database.DAO

import androidx.room.*
import com.example.mobilediary.database.Event
import com.example.mobilediary.database.Holiday

@Dao
interface HolidayDAO {
    @Query("SELECT * FROM user_holidays")
    fun getAllEvents(): List<Holiday>

    @Query("SELECT * FROM user_holidays WHERE idHoliday = :id")
    fun getEvent(id: Long): Holiday

    @Insert
    fun insertEvent(vararg holiday: Holiday)

    @Update
    fun updateEvent(holiday: Holiday)

    @Delete
    fun deleteEvent(vararg holiday: Holiday)
}