package com.example.avengers.di

import android.app.ProgressDialog
import android.content.Context
import android.widget.ProgressBar
import com.example.avengers.BuildConfig
import com.example.avengers.data.repository.CharacterRepositoryImpl
import com.example.avengers.data.service.AvengerService
import com.example.avengers.data.repository.CharactersRepository
import com.example.avengers.domain.usecase.AvengerUseCase
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit

private const val TIME_OUT = 30L

val NetworkModule = module {

    single { createService(get()) }

    single { createRetrofit(get(), BuildConfig.BASE_URL) }

    single { createOkHttpClient() }

    single { createProgressBar(androidContext()) }


}

fun createOkHttpClient(): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
    return OkHttpClient.Builder()
        .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
        .readTimeout(TIME_OUT, TimeUnit.SECONDS)
        .addInterceptor(httpLoggingInterceptor).build()
}

fun createRetrofit(okHttpClient: OkHttpClient, url: String): Retrofit {
    return Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
         .addConverterFactory(JacksonConverterFactory.create()).build()
}

fun createService(retrofit: Retrofit): AvengerService {
    return retrofit.create(AvengerService::class.java)
}


fun createPostRepository(apiService: AvengerService): CharactersRepository {
    return CharacterRepositoryImpl(apiService)
}

fun createGetPostsUseCase(postsRepository: CharactersRepository): AvengerUseCase {
    return AvengerUseCase(postsRepository)
}

fun createProgressBar(context : Context) {
    ProgressDialog(context)
}
