package com.danusuhendra.suitmediatest.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.danusuhendra.suitmediatest.model.response.Data

@Dao
interface GuestDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(guest: List<Data>?)

    @Query("SELECT * FROM guest_table")
    suspend fun getGuest() : List<Data>
}