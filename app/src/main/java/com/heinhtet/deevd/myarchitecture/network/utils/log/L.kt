package com.heinhtet.deevd.myarchitecture.network.utils.log

import android.util.Log
import com.heinhtet.deevd.myarchitecture.BuildConfig

/**
 * Created by Hein Htet on 8/23/18.
 */

object L {
    private val TAG = "DeevD-HH : "
    fun i(tag: String = TAG, message: String) {
        if (isEnable()) Log.i(tag, message)
    }

    fun wtf(tag: String = TAG, message: String) {
        if (isEnable()) Log.i("$tag  WTF : ", message)
    }

    private fun isEnable(): Boolean {
        return BuildConfig.L_ENABLE
    }
}