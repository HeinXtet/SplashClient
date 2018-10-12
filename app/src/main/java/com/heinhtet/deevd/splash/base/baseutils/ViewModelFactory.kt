package com.heinhtet.deevd.splash.base.baseutils

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.heinhtet.deevd.splash.data.DataService
import com.heinhtet.deevd.splash.ui.auth.AuthViewModel
import com.heinhtet.deevd.splash.ui.home.HomeViewModel
import com.heinhtet.deevd.splash.ui.main.MainViewModel

/**
 * Created by Hein Htet on 8/21/18.
 */
class ViewModelFactory(private val dataManager: DataService) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_ CAST")
            return MainViewModel(dataManager) as T
        } else if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(dataManager) as T
        }else if (modelClass.isAssignableFrom(AuthViewModel::class.java)){
            return AuthViewModel(dataManager) as T

        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}