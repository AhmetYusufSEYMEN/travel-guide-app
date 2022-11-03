package com.seymen.seymentravel.utils

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.seymen.seymentravel.R

class DataBindingAdapters {
    companion object {
        @JvmStatic
        @BindingAdapter("imageUrl")
        fun setImageUrl(imgView: ImageView, imgUrl: String?) {
            imgUrl?.let {
                val imgUri = it.toUri().buildUpon().scheme("https").build()
                Glide.with(imgView.context)
                    .load(imgUri)
                    .apply(
                        RequestOptions()
                            .placeholder(R.drawable.image_loading_animation)
                            .error(R.drawable.error)
                    )
                    .into(imgView)
            }
        }
    }

    /* fun Fragment.onBackPressedAction(action: () -> Boolean) {
         requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object :
             OnBackPressedCallback(true) {
             override fun handleOnBackPressed() {
                 this.isEnabled = action()
                 if (!this.isEnabled) {
                     requireActivity().onBackPressed()
                 }
             }
         })
     }*/

    /*@BindingAdapter("downloadFromUrl")
    fun ImageView.downloadFromUrl(img_src: String?) {

    img_src?.let {
        //https sorununu Bu kod yerine manifest dosyasına usesCleartextTraffic=true değeri ekleyerek giderilmiştir.
        //val imgUri = img_src.toUri().buildUpon().scheme("https").build()
        Glide.with(context)
            .load(img_src)
            .apply(RequestOptions()
                .placeholder(R.drawable.loading_image)
                .error(R.drawable.error)
            )
            .into(this)
    }
}

/**
 * @param img_src marsDataModelden gelen img_src değerlerini view yardımıyla göstermemizi sağlıyor.
 */
@BindingAdapter("android:downloadImageUrl")
fun downloadImage(view: ImageView, img_src: String?) {
    view.downloadFromUrl(img_src)
}*/

}

