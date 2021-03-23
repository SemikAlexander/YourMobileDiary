package com.example.mobilediary.database.DAO

import androidx.room.*
import com.example.mobilediary.database.Event

@Dao
interface EventDAO {
    @Query("SELECT * FROM user_events")
    fun getAllEvents(): List<Event>

    @Query("SELECT * FROM user_events WHERE idEvent = :id")
    fun getEvent(id: Int): Event

    @Insert
    fun insertEvent(vararg event: Event)

    @Update
    fun updateEvent(event: Event)

    @Delete
    fun deleteEvent(vararg event: Event)
}