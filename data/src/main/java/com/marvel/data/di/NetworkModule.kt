package com.marvel.data.di

import com.marvel.data.characters.api.CharactersApiService
import com.marvel.domain.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import javax.inject.Singleton

/**
 * This class is for Retrofit
 */
@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {


    @Provides
    fun provideBaseUrl() = BuildConfig.BASE_URL

    @Provides
    @Singleton
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    } else {
        OkHttpClient.Builder().build()
    }

    @Singleton
    @Provides
    fun getRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(JacksonConverterFactory.create())
            .baseUrl(provideBaseUrl())
            .client(provideOkHttpClient())
            .build()
    }

    fun createCharacterApi(): CharactersApiService {
        val retrofit = getRetrofitInstance()
        return retrofit.create(CharactersApiService::class.java)
    }

}
