package com.heinhtet.deevd.myarchitecture.network.ui.home

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.heinhtet.deevd.myarchitecture.R
import com.heinhtet.deevd.myarchitecture.network.base.baseutils.Injection
import com.heinhtet.deevd.myarchitecture.network.model.response.GithubUser
import com.heinhtet.deevd.myarchitecture.network.network.NetworkState
import com.heinhtet.deevd.myarchitecture.network.utils.locale.LocaleHelper
import com.heinhtet.deevd.myarchitecture.network.utils.log.L

/**
 * Created by Hein Htet on 8/22/18.
 */

class HomeActivity : AppCompatActivity() {

    private lateinit var viewModel: HomeViewModel
    private val TAG = "HomeActivity "

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        LocaleHelper.setLocale(this, LocaleHelper.getMultiMM(this))

        viewModel = ViewModelProviders.of(this, Injection.provideViewModelFactory(this)).get(
                HomeViewModel::class.java
        )
        addObserver()
        L.i(message = this.getString(R.string.Project))
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        LocaleHelper.onAttach(this)
    }

    private fun addObserver() {
        viewModel.getNetworkState().observe(this, Observer<NetworkState> {
            Log.i(TAG, " network state ${it?.message}")
        })
        viewModel.testingObserve().observe(this, Observer<Boolean> {
            Log.i(TAG, " is MM language  ${it}")
        })

        viewModel.gitHubUserList.observe(this, Observer<List<GithubUser>> {
            Log.i(TAG, " GITHUB userList ${it.toString()}")
        })
    }
}