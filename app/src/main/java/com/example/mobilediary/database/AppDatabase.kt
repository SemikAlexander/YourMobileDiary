package com.example.mobilediary.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mobilediary.database.DAO.BirthdayDAO
import com.example.mobilediary.database.DAO.EventDAO
import com.example.mobilediary.database.DAO.HolidayDAO

@Database(entities = [Event::class, Birthday::class, Holiday::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun eventUserDao(): EventDAO
    abstract fun birthdayUserDao(): BirthdayDAO
    abstract fun holidayUserDao(): HolidayDAO

    companion object {
        @Volatile private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context)= instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { instance = it}
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
                AppDatabase::class.java, "mob-diary.db")
                .allowMainThreadQueries()
                .build()
    }
}