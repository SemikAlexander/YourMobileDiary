package com.example.mobilediary.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "user_events")
data class Event(
    @PrimaryKey(autoGenerate = true)
    var idEvent: Long,

    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "description") var description: String,
    @ColumnInfo(name = "date") var date: Long
)

@Entity(tableName = "user_holidays")
class Holiday(
    @PrimaryKey(autoGenerate = true)
    var idHoliday: Long,

    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "description") var description: String,
    @ColumnInfo(name = "date") var date: Long
)

@Entity(tableName = "user_birthdays")
class Birthday(
    @PrimaryKey(autoGenerate = true)
    var idBirthday: Long,

    @ColumnInfo(name = "namePerson") var namePerson: String,
    @ColumnInfo(name = "date") var date: Long
)