package com.heinhtet.deevd.splash.ui.home

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.heinhtet.deevd.splash.R
import com.heinhtet.deevd.splash.base.baseutils.Injection
import com.heinhtet.deevd.splash.model.response.GithubUser
import com.heinhtet.deevd.splash.network.NetworkState
import com.heinhtet.deevd.splash.utils.locale.LocaleHelper
import com.heinhtet.deevd.splash.utils.log.L

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

            when (it){
                NetworkState.LOADING ->{

                }
                NetworkState.LOADED->{

                }
            }

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