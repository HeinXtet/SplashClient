package com.heinhtet.deevd.splash.base.baseutils

import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import com.heinhtet.deevd.splash.data.DataService

/**
 * Created by Hein Htet on 8/22/18.
 */
object Injection {


    private fun provideDataService(context: Context): DataService {
        return DataService(context)
    }
    /**
     * Provides the [ViewModelProvider.Factory] that is then used to get a reference to
     * [ViewModel] objects.
     */
    fun provideViewModelFactory(context: Context): ViewModelProvider.Factory {
        return ViewModelFactory(provideDataService(context))
    }



}