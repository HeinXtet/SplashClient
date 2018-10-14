package com.heinhtet.deevd.splash.utils.view

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import com.heinhtet.deevd.splash.extensions.h
import com.heinhtet.deevd.splash.extensions.s
import com.heinhtet.deevd.splash.network.NetworkState
import com.heinhtet.deevd.splash.utils.log.L
import android.view.ViewGroup
import android.util.TypedValue
import android.util.DisplayMetrics




/**
 * Created by Hein Htet on 10/13/18.
 */
class ViewHelper(private val context: Context) {

    private val TAG = "ViewHelper"

    /* Handling Progress Bar */
    fun loading(view: View, state: NetworkState) {
        when (state) {
            NetworkState.LOADING -> {
                if (view.visibility == View.GONE) {
                    view.s()
                }
            }
            NetworkState.LOADED -> {
                if (view.visibility == View.VISIBLE) {
                    view.h()
                }
            }
        }
    }

    fun initStatusBar(activity: AppCompatActivity, toolbar: Toolbar) {
        activity.setSupportActionBar(toolbar)

        activity.supportActionBar?.title = ""

    }

    fun setMargins(v: View, l: Int, t: Int, r: Int, b: Int) {
        if (v.layoutParams is ViewGroup.MarginLayoutParams) {
            val p = v.layoutParams as ViewGroup.MarginLayoutParams
            p.setMargins(l, t, r, b)
            v.requestLayout()
        }
    }

    fun getToolBarHeight(): Int {
        // Calculate ActionBar height
        val tv = TypedValue()
        if (context.theme.resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            return tv.data
            //return TypedValue.complexToDimensionPixelSize(tv.data, context.resources.displayMetrics)
        }
        return 0
    }

    fun intent(from: AppCompatActivity, to: AppCompatActivity, isFinish: Boolean = false) {
        from.startActivity(Intent(from, to::class.java))
        if (isFinish) from.finish()
    }

    fun getStatusBarHeight(): Int {
        var result = 0
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = context.resources.getDimensionPixelSize(resourceId)
        }
        L.i(TAG, "status bar height $result")
        return result
    }

    fun convertDpToPixel(dp: Float): Float {
        val resources = context.resources
        val metrics = resources.displayMetrics
        return dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     */
    fun convertPixelsToDp(px: Float): Float {
        return px / (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }

}