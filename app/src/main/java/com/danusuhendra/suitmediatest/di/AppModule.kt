package com.danusuhendra.suitmediatest.di

import com.danusuhendra.suitmediatest.network.ApiInterface
import com.danusuhendra.suitmediatest.repository.GuestRepository
import com.danusuhendra.suitmediatest.utils.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRxJava2CallAdapterFactory(): RxJava2CallAdapterFactory {
        return RxJava2CallAdapterFactory.create()
    }

    @Singleton
    @Provides
    fun provideService(
        rxJava2CallAdapterFactory: RxJava2CallAdapterFactory,
    ) : ApiInterface {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val clientBuilder = OkHttpClient.Builder()
        clientBuilder.addInterceptor(logging)
        val client = clientBuilder.build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(rxJava2CallAdapterFactory)
            .client(client)
            .build()
        return retrofit.create(ApiInterface::class.java)
    }

    @Singleton
    @Provides
    fun provideGuestRepository(apiInterface: ApiInterface) : GuestRepository {
        return GuestRepository(apiInterface)
    }

}