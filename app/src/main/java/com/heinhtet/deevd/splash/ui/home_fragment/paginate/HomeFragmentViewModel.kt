package com.heinhtet.deevd.splash.ui.home_fragment.paginate

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.heinhtet.deevd.splash.data.DataService
import com.heinhtet.deevd.splash.model.response.Movie
import com.heinhtet.deevd.splash.model.response.PhotoModel
import com.heinhtet.deevd.splash.network.NetworkState
import com.heinhtet.deevd.splash.ui.main.paginate.MovieDataFactory
import com.heinhtet.deevd.splash.ui.main.paginate.MovieDataSource
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Hein Htet on 10/14/18.
 */
class HomeFragmentViewModel(manager : DataService) : ViewModel() {
    var photoListData: LiveData<PagedList<PhotoModel>>

    private val compositeDisposable = CompositeDisposable()

    private val pageSize = 2

    private val sourceFactory: PhotoDataFactory

    init {
        sourceFactory = PhotoDataFactory(compositeDisposable, manager)
        val config = PagedList.Config.Builder()
                .setPageSize(pageSize)
                .setInitialLoadSizeHint(pageSize * 2)
                .setEnablePlaceholders(false)
                .build()
        photoListData = LivePagedListBuilder<Long, PhotoModel>(sourceFactory, config).build()
    }

    fun retry() {
        sourceFactory.dataSourceLiveData.value!!.retry()
    }

    fun refresh() {
        sourceFactory.dataSourceLiveData.value!!.invalidate()
    }


    fun getNetworkState(): LiveData<NetworkState> = Transformations.switchMap(
            sourceFactory.dataSourceLiveData) {
        it.networkState
    }


    fun getRefreshState(): LiveData<NetworkState> = Transformations.switchMap<PhotoDataSource, NetworkState>(
            sourceFactory.dataSourceLiveData) { it.initialLoad }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}