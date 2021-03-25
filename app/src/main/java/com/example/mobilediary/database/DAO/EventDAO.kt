package com.example.mobilediary.database.DAO

import androidx.room.*
import com.example.mobilediary.database.Event

@Dao
interface EventDAO {
    @Query("SELECT * FROM user_events")
    fun getAllEvents(): List<Event>

    @Query("SELECT * FROM user_events WHERE idEvent = :id")
    fun getEvent(id: Long): Event

    @Query("UPDATE user_events SET title = :title, description = :description, date = :date")
    fun update(title: String, description: String, date: Long)

    @Insert
    fun insertEvent(vararg event: Event)

    @Update
    fun updateEvent(event: Event)

    @Delete
    fun deleteEvent(vararg event: Event)
}