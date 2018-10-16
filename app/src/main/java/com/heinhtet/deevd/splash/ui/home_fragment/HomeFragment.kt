package com.heinhtet.deevd.splash.ui.home_fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.paging.PagedList
import android.content.Context
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import butterknife.BindView
import com.google.gson.Gson
import com.heinhtet.deevd.splash.R
import com.heinhtet.deevd.splash.base.baseutils.Injection
import com.heinhtet.deevd.splash.base.baseview.BaseFragment
import com.heinhtet.deevd.splash.data.PrefHelper
import com.heinhtet.deevd.splash.model.response.PhotoModel
import com.heinhtet.deevd.splash.network.NetworkState
import com.heinhtet.deevd.splash.network.Status
import com.heinhtet.deevd.splash.ui.home_fragment.adapter.PhotoAdapter
import com.heinhtet.deevd.splash.ui.home_fragment.paginate.HomeFragmentViewModel
import com.heinhtet.deevd.splash.utils.log.L
import com.heinhtet.deevd.splash.utils.view.ViewHelper
import kotlinx.android.synthetic.main.item_network_state.*
import com.google.gson.reflect.TypeToken
import com.heinhtet.deevd.splash.ui.home_fragment.paginate.PhotoDataFactory
import com.heinhtet.deevd.splash.ui.home_fragment.paginate.PhotoDataSource


/**
 * Created by Hein Htet on 10/14/18.
 */

class HomeFragment : BaseFragment() {
    override fun getLayoutId() = R.layout.rv_layout
    private lateinit var fragmentActivity: FragmentActivity
    private lateinit var viewModel: HomeFragmentViewModel
    private val TAG = "HomeFragment"
    private lateinit var adapter: PhotoAdapter

    @BindView(R.id.rv)
    lateinit var rv: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this, Injection.provideViewModelFactory(fragmentActivity)).get(HomeFragmentViewModel::class.java)
        initRv()
        addObserver()
        L.i(TAG, "HomeFragment ${PrefHelper.instance().getPhotoModel()} photoModel")
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        fragmentActivity = context as FragmentActivity
    }


    private fun initRv() {

        adapter = PhotoAdapter {
            viewModel.retry()
        }
        if (fragmentActivity.resources.getBoolean(R.bool.is_land)) {
            L.i(TAG, "is LandScape")
            val gridLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            rv.addItemDecoration(ViewHelper(fragmentActivity).SpacesItemDecoration(8))
            rv.layoutManager = gridLayoutManager
        } else {
            val layoutManager = LinearLayoutManager(fragmentActivity)
            rv.layoutManager = layoutManager
        }
        rv.isNestedScrollingEnabled = true
        rv.adapter = adapter
    }


    private fun addObserver() {
        viewModel.photoListData.observe(this, Observer<PagedList<PhotoModel>> {
            L.i(TAG, "Submit Page ${it.toString()}")
            if (it != null) {
                adapter.submitList(it)
                PrefHelper.instance().setPhotoModel(it!!)
            }
        })
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