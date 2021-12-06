package com.marvel.domain.model

import com.marvel.domain.BuildConfig
import java.math.BigInteger
import java.security.MessageDigest


data class CharactersRequestModel(
    val ts: Long = System.currentTimeMillis(),
    val apiKey: String = BuildConfig.PUBLIC_API_KEY,
    val hashKey: String = getMd5(ts),
    var id: String = ""
)


fun getMd5(timestamp: Long): String {
    val input = timestamp.toString() + BuildConfig.PRIVATE_API_KEY + BuildConfig.PUBLIC_API_KEY
    val md = MessageDigest.getInstance("MD5")
    return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')

}
