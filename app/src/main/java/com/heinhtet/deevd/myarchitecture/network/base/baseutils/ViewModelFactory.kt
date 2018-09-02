package com.heinhtet.deevd.myarchitecture.network.base.baseutils

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.heinhtet.deevd.myarchitecture.network.data.DataService
import com.heinhtet.deevd.myarchitecture.network.ui.home.HomeViewModel
import com.heinhtet.deevd.myarchitecture.network.ui.main.MainViewModel

/**
 * Created by Hein Htet on 8/21/18.
 */
class ViewModelFactory(private val dataManager: DataService) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(dataManager) as T
        } else if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(dataManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}