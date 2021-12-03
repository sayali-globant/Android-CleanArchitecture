package com.example.marvels.di

import com.example.marvels.BuildConfig
import com.marvel.data.characters.api.CharactersApiHelper
import com.marvel.data.characters.api.CharactersApiHelperImpl
import com.marvel.data.characters.api.CharactersApiService
import com.marvel.data.details.api.CharacterDetailsApiHelper
import com.marvel.data.details.api.CharacterDetailsApiHelperImpl
import com.marvel.data.details.api.CharacterDetailsApiService
import com.marvel.domain.usecase.characters.GetCharactersUseCase
import com.marvel.domain.usecase.characters.GetCharactersUseCaseImpl
import com.marvel.domain.usecase.details.GetCharacterDetailsUseCase
import com.marvel.domain.usecase.details.GetCharacterDetailsUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

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

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, baseUrl: String): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(JacksonConverterFactory.create())
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideCharactersApiService(retrofit: Retrofit) =
        retrofit.create(CharactersApiService::class.java)

    @Provides
    @Singleton
    fun provideCharacterApiHelper(apiHelperImpl: CharactersApiHelperImpl): CharactersApiHelper =
        apiHelperImpl

    @Provides
    @Singleton
    fun provideCharacterUseCaseHelper(useCaseHelperImpl: GetCharactersUseCaseImpl): GetCharactersUseCase =
        useCaseHelperImpl

    @Provides
    @Singleton
    fun provideDetailsApiService(retrofit: Retrofit) =
        retrofit.create(CharacterDetailsApiService::class.java)

    @Provides
    @Singleton
    fun provideDetailsApiHelper(apiHelperImpl: CharacterDetailsApiHelperImpl): CharacterDetailsApiHelper =
        apiHelperImpl

    @Provides
    @Singleton
    fun provideDetailsUseCaseHelper(useCaseHelperImpl: GetCharacterDetailsUseCaseImpl): GetCharacterDetailsUseCase =
        useCaseHelperImpl


}
