package com.heinhtet.deevd.myarchitecture.network.ui.main

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.heinhtet.deevd.myarchitecture.network.ui.main.paginate.MovieDataSource
import com.heinhtet.deevd.myarchitecture.network.data.DataService
import com.heinhtet.deevd.myarchitecture.network.model.response.Movie
import com.heinhtet.deevd.myarchitecture.network.network.NetworkState
import com.heinhtet.deevd.myarchitecture.network.ui.main.paginate.MovieDataFactory
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Hein Htet on 8/21/18.
 */
class MainViewModel(manager: DataService) : ViewModel() {


    var movieListLiveData: LiveData<PagedList<Movie>>

    private val compositeDisposable = CompositeDisposable()

    private val pageSize = 20

    private val sourceFactory: MovieDataFactory

    init {
        sourceFactory = MovieDataFactory(compositeDisposable, manager)
        val config = PagedList.Config.Builder()
                .setPageSize(pageSize)
                .setInitialLoadSizeHint(pageSize * 2)
                .setEnablePlaceholders(false)
                .build()
        movieListLiveData = LivePagedListBuilder<Long, Movie>(sourceFactory, config).build()
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


    fun getRefreshState(): LiveData<NetworkState> = Transformations.switchMap<MovieDataSource, NetworkState>(
            sourceFactory.dataSourceLiveData) { it.initialLoad }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}