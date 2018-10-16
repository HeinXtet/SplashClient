package com.heinhtet.deevd.splash.extensions

import android.app.Activity
import android.content.Context
import android.graphics.BitmapFactory
import android.support.annotation.IdRes
import android.support.v4.graphics.drawable.RoundedBitmapDrawable
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.support.v7.widget.AppCompatImageView
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.ObjectKey
import com.heinhtet.deevd.splash.R
import com.heinhtet.deevd.splash.utils.glideutils.GlideApp
import android.animation.ObjectAnimator
import com.bumptech.glide.GenericTransitionOptions
import com.bumptech.glide.request.transition.ViewPropertyTransition


/**
 * Created by Hein Htet on 8/22/18.
 */

fun AppCompatImageView.load(path: String, circle: Boolean = false, chache: Boolean = false) {
    val requestOptions: RequestOptions = if (circle) {
        getCircleRequestOption(context, chache)
    } else {
        getDefaultRequestOption(chache)
    }

    GlideApp.with(context).load((path)).apply(requestOptions)
            .transition(GenericTransitionOptions.with(R.anim.zoom_in))
            .into(this)

}

fun getGlideAnimation()  : ViewPropertyTransition.Animator{
    return ViewPropertyTransition.Animator { view ->
        val ani = ObjectAnimator.ofFloat(view, "translationX", 1f, 1f)
        ani.duration = 2500
        ani.interpolator = AccelerateDecelerateInterpolator()
        ani.start()
    }
}

fun getCircleRequestOption(context: Context, isCache: Boolean): RequestOptions {
    val circularBitmapDrawable = getRoundPlaceholder(context)
    circularBitmapDrawable.isCircular = true
    var req = RequestOptions.circleCropTransform().placeholder(circularBitmapDrawable).error(circularBitmapDrawable)
    if (isCache) {
        req = req.signature(ObjectKey(System.currentTimeMillis()))
    }
    return req
}

fun getDefaultRequestOption(isCache: Boolean): RequestOptions {
    var requestOptions = RequestOptions()
    if (isCache) {
        requestOptions = requestOptions.signature(ObjectKey(System.currentTimeMillis()))
    }
    requestOptions = requestOptions.placeholder(R.color.colorPrimary).error(R.color.colorPrimary)
    return requestOptions
}


fun View.leftToRight(offset: Float, duration: Long = 2000) {
    this.animate().translationX(offset).setDuration(duration).interpolator = AccelerateDecelerateInterpolator()
}

fun AppCompatImageView.load(id: Int, circle: Boolean = false) {
    val circularBitmapDrawable = getRoundPlaceholder(context)
    var requestOptions = RequestOptions.circleCropTransform()
            .placeholder(circularBitmapDrawable)
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
    val placeholder = BitmapFactory.decodeResource(context.resources, R.color.colorAccent)
    return RoundedBitmapDrawableFactory.create(context.resources, placeholder)
}