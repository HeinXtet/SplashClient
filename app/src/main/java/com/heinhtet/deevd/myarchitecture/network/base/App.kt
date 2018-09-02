package com.heinhtet.deevd.myarchitecture.network.base

import android.app.Application
import android.content.res.Configuration
import com.heinhtet.deevd.myarchitecture.network.data.PrefHelper
import com.heinhtet.deevd.myarchitecture.network.utils.locale.LocaleHelper

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