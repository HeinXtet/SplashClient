package com.heinhtet.deevd.myarchitecture.network.data

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson

/**
 * Created by Hein Htet on 8/23/18.
 */
class PrefHelper {
    private val NAME = "ProjectName"
    private lateinit var mContext: Context
    private lateinit var sharePref: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private val Language = "lan"

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var mInstance: PrefHelper? = null

        fun instance(): PrefHelper {
            if (mInstance == null) {
                synchronized(PrefHelper::class.java)
                {
                    if (mInstance == null) {
                        mInstance = PrefHelper()
                    }
                }
            }
            return mInstance!!
        }
    }

    @SuppressLint("CommitPrefEdits")
    fun init(context: Context) {
        mContext = context
        sharePref = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE)
        editor = sharePref.edit()
    }

    fun clear() {
        editor.clear().apply()
    }

    fun isMmLanguage(): Boolean {
        return sharePref.getBoolean(Language, true)
    }

    fun setMmLanguage(isMM: Boolean) {
        editor.putBoolean(Language, isMM).apply()
    }

    fun setObj(obj: Any) {
        editor.putString("OBJ", Gson().toJson(obj)).apply()
    }

    fun getObj(): Any {
        return Gson().fromJson<Any>(sharePref.getString("OBJ", ""), Any::class.java)
    }


}