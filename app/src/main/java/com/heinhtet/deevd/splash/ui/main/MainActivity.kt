package com.heinhtet.deevd.splash.ui.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.paging.PagedList
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.heinhtet.deevd.splash.R
import com.heinhtet.deevd.splash.base.baseutils.Injection
import com.heinhtet.deevd.splash.model.response.Movie
import com.heinhtet.deevd.splash.network.NetworkState
import com.heinhtet.deevd.splash.network.Status
import kotlinx.android.synthetic.main.item_network_state.*

class MainActivity : AppCompatActivity() {

    val rv by lazy {
        findViewById<RecyclerView>(R.id.rv)
    }

    lateinit var adapter: RvAdapter

    lateinit var viewModel: MainViewModel
    val TAG = "MainActivity "
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initRv()
        viewModel = ViewModelProviders.of(this, Injection.provideViewModelFactory(this)).get(MainViewModel::class.java)
        addObserver()
    }


    private fun initRv() {
        adapter = RvAdapter {
            viewModel.retry()
        }
        val layoutManager = LinearLayoutManager(this)
        rv.layoutManager = layoutManager
        rv.adapter = adapter
    }


    private fun addObserver() {
        viewModel.movieListLiveData.observe(this, Observer<PagedList<Movie>> { adapter.submitList(it) })
        viewModel.getNetworkState().observe(this, Observer<NetworkState> { adapter.setNetworkState(it) })
        viewModel.getRefreshState().observe(this, Observer { networkState ->
            setInitialLoadingState(networkState)
        })
    }

    /**
     * Show the current network state for the first load when the user list
     * in the adapter is empty and disable swipe to scroll at the first loading
     *
     * @param networkState the new network state
     */
    private fun setInitialLoadingState(networkState: NetworkState?) {
        //error message
        errorMessageTextView.visibility = if (networkState?.message != null) View.VISIBLE else View.GONE
        if (networkState?.message != null) {
            errorMessageTextView.text = networkState.message
        }

        //loading and retry
        retryLoadingButton.visibility = if (networkState?.status == Status.FAILED) View.VISIBLE else View.GONE
        loadingProgressBar.visibility = if (networkState?.status == Status.RUNNING) View.VISIBLE else View.GONE

        retryLoadingButton.setOnClickListener { viewModel.retry() }
    }
}
