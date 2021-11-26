package com.example.avengers.data.service

import com.example.avengers.BuildConfig
import com.example.avengers.domain.model.AvengerCharacterResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AvengerService {

    @GET("public/characters")
    suspend fun getMarvelCharacters(@Query ("ts") ts : Long,
                                    @Query ("apikey") apikey : String,
                                    @Query ("hash") hash : String,
                                    @Query ("limit") limit : Int,
                                    @Query ("offset") offset : Int,
    ): AvengerCharacterResponse
}