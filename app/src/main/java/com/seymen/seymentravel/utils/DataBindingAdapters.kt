package com.seymen.seymentravel.utils

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.seymen.seymentravel.R

class DataBindingAdapters {
    companion object {
        @JvmStatic @BindingAdapter("imageUrl")
        fun setImageUrl(imgView: ImageView, imgUrl: String?){
            imgUrl?.let {
                val imgUri = it.toUri().buildUpon().scheme("https").build()
                Glide.with(imgView.context)
                    .load(imgUri)
                    .apply(
                        RequestOptions()
                            .placeholder(R.drawable.loading)
                            .error(R.drawable.error))
                    .into(imgView)
            }
        }
    }
}

