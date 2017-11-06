package com.ljubinkovicd.comicdroid.helper

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.ljubinkovicd.comicdroid.R
import com.squareup.picasso.Picasso

/**
 * Created by ljubinkovicd on 11/5/17.
 */
fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)
}

fun ImageView.loadImgFromUrl(imageUrl: String) {
    if (TextUtils.isEmpty(imageUrl)) {
        Picasso.with(context)
                .load(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(this)
    } else {
        Picasso.with(context)
                .load(imageUrl)
                .fit()
                .centerCrop()
                .into(this)
    }
}