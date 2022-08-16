package com.danusuhendra.suitmediatest.repository

import com.danusuhendra.suitmediatest.db.GuestDao
import com.danusuhendra.suitmediatest.model.response.Data
import javax.inject.Inject

class GuestDbRepository @Inject constructor(private val guestDao: GuestDao) {

    suspend fun insert(guest: List<Data>?) {
        guestDao.insert(guest)
    }

    suspend fun getGuestDb(onQuery: (List<Data>) -> Unit) {
        val guest = guestDao.getGuest()
        onQuery(guest)
    }
}