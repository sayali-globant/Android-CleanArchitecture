package com.example.marvels.domain.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.BindingAdapter
import com.bumptech.glide.request.RequestOptions
import com.example.marvels.R
import com.example.marvels.common.GlideApp


@BindingAdapter("profileImage")
fun setImage(imageView: ImageView, url: Any?) {
    val options: RequestOptions = RequestOptions()
        .centerCrop()
        .placeholder(R.mipmap.ic_launcher_round)//.dontAnimate()
        .error(R.mipmap.ic_launcher)

    GlideApp.with(imageView.context).load(url)
        .apply(options).into(imageView)
}


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
