package com.heinhtet.deevd.splash.utils.locale

import android.content.Context
import android.view.ViewGroup
import android.widget.TextView
import com.heinhtet.deevd.splash.data.PrefHelper

/**
 * Created by Hein Htet on 8/23/18.
 */
class Mm(val context: Context) {

    fun isUniCode(): Boolean {
        val textView = TextView(context, null)
        textView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)

        textView.text = "\u1000"
        textView.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val length1 = textView.measuredWidth
        textView.text = "\u1000\u1039\u1000"
        textView.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val length2 = textView.measuredWidth

        return length1 == length2
    }


    fun MM(mm: String, eng: String): String {
        if (PrefHelper.instance().isMmLanguage()) {
            if (isUniCode()) {
                return mm
            } else {
                return Rabbit.uni2zg(mm)
            }
        } else {
            return eng
        }
    }

    fun getLanguage(): String {
        return if (PrefHelper.instance().isMmLanguage()) {
            return "my"
        } else {
            return "en"
        }
    }

    fun getSearchQuery(str: String): String {
        if (isUniCode()) {
            return str
        } else {
            return Rabbit.zg2uni(str)
        }
    }
}