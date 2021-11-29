package com.example.marvels.data.service

import com.example.marvels.data.entity.AvengerCharacterResponse
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