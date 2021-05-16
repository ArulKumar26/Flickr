package com.flickr.utils.binding

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.flickr.R
import com.flickr.extensions.isNullOrEmpty


/**
 * @param imageView
 * @param imageUrl
 * @param progressBar
 */
@BindingAdapter(value = ["imageUrl", "progressBar"], requireAll = false)
fun setImageUrl(imageView: ImageView, imageUrl: String?, progressBar: ProgressBar? = null) {
    if (!isNullOrEmpty(imageUrl)) {
        Glide.with(imageView.context)
            .load(imageUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    imageView.setImageResource(R.drawable.ic_picture)
                    progressBar?.visibility = View.GONE
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    progressBar?.visibility = View.GONE
                    return false
                }
            })
            .into(imageView)
    }
}

/**
 * Hide the view based on condition
 */
@BindingAdapter(value = ["isVisible"], requireAll = false)
fun setIsVisible(view: View, isVisible: Boolean) {
    if (isVisible) {
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.GONE
    }
}
