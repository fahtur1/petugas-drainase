package com.pcr.drainit.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.pcr.drainit.utill.Constant

@BindingAdapter("app:loadImgFromUrl")
fun ImageView.loadImgFromUrl(url: String) {
    val fullUrl = Constant.IMG_URL + url
    Glide.with(this.context).load(fullUrl).into(this)
}