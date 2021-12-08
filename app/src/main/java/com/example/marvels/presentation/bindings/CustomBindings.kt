package com.example.marvels.presentation.bindings

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.request.RequestOptions
import com.example.marvels.R
import com.example.marvels.common.GlideApp

@BindingAdapter("profileImage")
fun setImage(imageView: ImageView, url: Any?) {
    val options: RequestOptions = RequestOptions()
        .centerCrop()
        .placeholder(R.mipmap.ic_launcher_round)
        .error(R.mipmap.ic_launcher)

    GlideApp.with(imageView.context).load(url)
        .apply(options).into(imageView)
}
