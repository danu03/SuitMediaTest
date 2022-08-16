package com.danusuhendra.suitmediatest.di

import android.content.Context
import androidx.room.Room
import com.danusuhendra.suitmediatest.db.AppDatabase
import com.danusuhendra.suitmediatest.db.GuestDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    @Provides
    fun provideGuestDao(appDatabase: AppDatabase) : GuestDao {
        return appDatabase.guestDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "guest_database"
        ).build()
    }
}