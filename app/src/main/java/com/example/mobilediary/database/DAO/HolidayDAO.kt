package com.example.mobilediary.database.DAO

import androidx.room.*
import com.example.mobilediary.database.Holiday

@Dao
interface HolidayDAO {
    @Query("SELECT * FROM user_holidays")
    fun getAllHolidays(): List<Holiday>

    @Query("SELECT * FROM user_holidays WHERE idHoliday = :id")
    fun getHoliday(id: Long): Holiday

    @Query("UPDATE user_holidays SET title = :title, description = :description, date = :date")
    fun update(title: String, description: String, date: Long)

    @Insert
    fun insertHoliday(vararg holiday: Holiday)

    @Update
    fun updateHoliday(holiday: Holiday)

    @Delete
    fun deleteHoliday(vararg holiday: Holiday)
}