package com.example.marvels.domain.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.marvels.R


@BindingAdapter("profileImage")
fun setImage(imageView: ImageView, url: Any) {
    val options: RequestOptions = RequestOptions()
        .centerCrop().diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        .placeholder(R.mipmap.ic_launcher_round).dontAnimate()
        .error(R.mipmap.ic_launcher)

    Glide.with(imageView.context).load(url)
        .apply(options).into(imageView)
}
