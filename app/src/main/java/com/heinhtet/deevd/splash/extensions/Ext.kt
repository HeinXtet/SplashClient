package com.heinhtet.deevd.splash.extensions

import android.app.Activity
import android.content.Context
import android.graphics.BitmapFactory
import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v4.graphics.drawable.RoundedBitmapDrawable
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.support.v7.widget.AppCompatImageView
import android.view.View
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.ObjectKey
import com.heinhtet.deevd.splash.R
import com.heinhtet.deevd.splash.utils.glideutils.GlideApp

/**
 * Created by Hein Htet on 8/22/18.
 */

fun AppCompatImageView.load(path: String, circle: Boolean = false) {

    val circularBitmapDrawable = getRoundPlaceholder(context)
    var requestOptions = RequestOptions()
    requestOptions.placeholder(circularBitmapDrawable)

    if (circle) {
        circularBitmapDrawable.isCircular = true
        requestOptions = RequestOptions.circleCropTransform().placeholder(circularBitmapDrawable).error(circularBitmapDrawable).signature(ObjectKey(System.currentTimeMillis()))
    }
    GlideApp.with(context).load((path)).apply(requestOptions)
            .into(this)
}

fun AppCompatImageView.load(id: Int, circle: Boolean = false) {

    val circularBitmapDrawable = getRoundPlaceholder(context)
    var requestOptions = RequestOptions()
    requestOptions.placeholder(circularBitmapDrawable)

    if (circle) {
        circularBitmapDrawable.isCircular = true
        requestOptions = RequestOptions.circleCropTransform()
                .placeholder(circularBitmapDrawable).error(circularBitmapDrawable).signature(ObjectKey(System.currentTimeMillis()))
    }

    GlideApp.with(context).load((id)).apply(requestOptions)
            .into(this)
}

fun <T : View> Activity.bind(@IdRes res: Int): T {
    @Suppress("UNCHECKED_CAST")
    return findViewById(res)
}

fun View.s() {
    this.visibility = View.VISIBLE
}

fun View.h() {
    this.visibility = View.GONE
}

fun getRoundPlaceholder(context: Context): RoundedBitmapDrawable {
    val placeholder = BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher)
    return RoundedBitmapDrawableFactory.create(context.resources, placeholder)
}