package com.heinhtet.deevd.myarchitecture.network.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.heinhtet.deevd.myarchitecture.network.base.baseutils.BASE_IV_URL

/**
 * Created by Hein Htet on 8/22/18.
 */

fun ImageView.load(url:String){
    Glide.with(context).load(BASE_IV_URL +url).into(this)
}