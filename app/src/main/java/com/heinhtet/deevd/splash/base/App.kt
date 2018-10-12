package com.heinhtet.deevd.splash.base

import android.app.Application
import android.content.res.Configuration
import com.heinhtet.deevd.splash.data.PrefHelper
import com.heinhtet.deevd.splash.utils.locale.LocaleHelper

/**
 * Created by Hein Htet on 8/23/18.
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        PrefHelper.instance().init(this)
    }


    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        LocaleHelper.onAttach(this)
    }

}