package com.example.avengers.domain.model.request

import com.example.avengers.BuildConfig
import com.example.avengers.common.AppConstants
import java.math.BigInteger
import java.security.MessageDigest
import java.security.Timestamp

data class CharactersRequest(val ts : Long = System.currentTimeMillis(),
                             val apiKey : String = BuildConfig.PUBLIC_API_KEY,
                             val hashKey : String = getMd5(ts),
                             var limit : Int = AppConstants.PAGINATION_LIMIT,
                             var offset : Int = 1)


fun getMd5(timestamp: Long): String {
    val input = timestamp.toString() + BuildConfig.PRIVATE_API_KEY+BuildConfig.PUBLIC_API_KEY
    val md = MessageDigest.getInstance("MD5")
    return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')

}