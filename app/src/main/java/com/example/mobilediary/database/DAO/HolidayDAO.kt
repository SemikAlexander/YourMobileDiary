package com.example.mobilediary.database.DAO

import androidx.room.*
import com.example.mobilediary.database.Event
import com.example.mobilediary.database.Holiday

@Dao
interface HolidayDAO {
    @Query("SELECT * FROM user_holidays")
    fun getAllHolidays(): List<Holiday>

    @Query("SELECT * FROM user_holidays WHERE idHoliday = :id")
    fun getHoliday(id: Long): Holiday

    @Insert
    fun insertHoliday(vararg holiday: Holiday)

    @Update
    fun updateHoliday(holiday: Holiday)

    @Delete
    fun deleteHoliday(vararg holiday: Holiday)
}