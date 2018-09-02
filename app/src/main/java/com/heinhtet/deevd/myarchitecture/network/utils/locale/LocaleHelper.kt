package com.heinhtet.deevd.myarchitecture.network.utils.locale

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.preference.PreferenceManager
import java.util.Locale
import com.heinhtet.deevd.myarchitecture.network.data.PrefHelper


@SuppressLint("StaticFieldLeak")
/**
 * Created by Hein Htet on 8/23/18.
 */
object LocaleHelper {
    private val SELECTED_LANGUAGE = "Locale.Helper.Selected.Language"
    private val TAG = "locale helper"
    private lateinit var mContext: Context

    fun onAttach(context: Context): Context {
        mContext = context
        val lang = getPersistedData(context, Locale.getDefault().language)
        return setLocale(context, lang)
    }

    fun onAttach(context: Context, defaultLanguage: String): Context {
        val lang = getPersistedData(context, defaultLanguage)
        return setLocale(context, lang)
    }

    fun getLanguage(context: Context): String? {
        return getPersistedData(context, Locale.getDefault().language)
    }

    fun setLocale(context: Context, language: String?): Context {
        persist(context, language)
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            updateResources(context, language)
        } else updateResourcesLegacy(context, language)
    }

    private fun getPersistedData(context: Context, defaultLanguage: String): String? {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        return preferences.getString(SELECTED_LANGUAGE, defaultLanguage)
    }

    private fun persist(context: Context, language: String?) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = preferences.edit()
        editor.putString(SELECTED_LANGUAGE, language)
        editor.apply()
    }

    @TargetApi(Build.VERSION_CODES.N)
    private fun updateResources(context: Context, language: String?): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val conf = context.resources.configuration
        conf.locale = locale
        Locale.setDefault(locale)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            conf.setLayoutDirection(conf.locale)
        }
        context.resources.updateConfiguration(conf, context.resources.displayMetrics)
        return context.createConfigurationContext(conf)
    }

    private fun updateResourcesLegacy(context: Context, language: String?): Context {
        val res = context.resources
        val dm = res.displayMetrics
        val conf = res.configuration

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            conf.setLayoutDirection(conf.locale)
        }
        conf.locale = Locale(language, getCountry(language))
        res.updateConfiguration(conf, dm)
        return context.createConfigurationContext(conf)
    }


    private fun getCountry(language: String?): String {
        return when (language) {
            "my" -> "MM"
            "uk" -> "UK"
            "en" -> "En"
            else -> "En"
        }
    }

    fun getMultiMM(context: Context): String {
        if (PrefHelper.instance().isMmLanguage()) {
            if (Mm(context).isUniCode()) {
                return "my"
            } else {
                return "uk"
            }
        } else {
            return "en"
        }
    }


}