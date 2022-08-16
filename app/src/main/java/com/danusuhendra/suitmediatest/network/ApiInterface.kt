package com.danusuhendra.suitmediatest.network

import androidx.annotation.IntRange
import com.danusuhendra.suitmediatest.model.response.UserResponse
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("users")
    fun guest(
        @Query("page") page: Int = 1,
        @Query("per_page") @IntRange(from = 1, to = 80) perPage: Int = 10
    ) : Observable<Response<UserResponse>>
}