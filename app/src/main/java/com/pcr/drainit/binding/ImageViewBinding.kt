package com.pcr.drainit.binding

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.pcr.drainit.utill.Constant
import java.lang.NullPointerException

@BindingAdapter("app:loadImgFromUrl")
fun ImageView.loadImgFromUrl(url: String?) {
    val fullUrl = Constant.IMG_URL + url
    Glide.with(this.context).load(fullUrl).into(this)
}

@BindingAdapter("app:setImageWithURI")
fun ImageView.setImageWithURI(uri: Uri?) {
    this.setImageURI(uri)
}