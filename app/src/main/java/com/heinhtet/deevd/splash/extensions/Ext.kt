package com.heinhtet.deevd.splash.extensions

import android.app.Activity
import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.heinhtet.deevd.splash.base.baseutils.BASE_IV_URL

/**
 * Created by Hein Htet on 8/22/18.
 */

fun ImageView.load(url:String){
    Glide.with(context).load(BASE_IV_URL +url).into(this)


    fun <T : View> Activity.bind(@IdRes res : Int) : T {
        @Suppress("UNCHECKED_CAST")
        return findViewById(res)
    }

    fun <T : View> Fragment.bind(@IdRes res : Int) : T {
        @Suppress("UNCHECKED_CAST")
        return findViewById(res)
    }
}