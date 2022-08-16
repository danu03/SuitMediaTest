package com.danusuhendra.suitmediatest.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.danusuhendra.suitmediatest.model.response.Data

@Database(entities = [Data::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun guestDao() : GuestDao
}