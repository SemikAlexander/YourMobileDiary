package com.example.mobilediary.database.DAO

import androidx.room.*
import com.example.mobilediary.database.Birthday

@Dao
interface BirthdayDAO {
    @Query("SELECT * FROM user_birthdays")
    fun getAllBirthday(): List<Birthday>

    @Insert
    fun insertBirthday(vararg birthday: Birthday)

    @Update
    fun updateBirthday(birthday: Birthday)

    @Delete
    fun deleteBirthday(vararg birthday: Birthday)
}