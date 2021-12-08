package com.example.marvels.domain.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.widget.Toast


fun String.toast(context: Context, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, this, length).show()
}

fun Context.isNetworkAvailable(): Boolean {
    val result: Boolean

    val connectivityManager =
        this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val networkCapabilities = connectivityManager.activeNetwork ?: return false
    val activeNetwork =
        connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false

    result = when {
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
        else -> false
    }

    return result
}
