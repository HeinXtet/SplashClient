package com.heinhtet.deevd.myarchitecture.network.ui.main.paginate

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.PageKeyedDataSource
import android.util.Log
import com.heinhtet.deevd.myarchitecture.network.data.DataService
import com.heinhtet.deevd.myarchitecture.network.model.response.Movie
import com.heinhtet.deevd.myarchitecture.network.network.NetworkState
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers


/**
 * Created by Hein Htet on 8/21/18.
 */

val TAG = "DataSoruce"

class MovieDataSource(
        private val dataService: DataService,
        private val compositeDisposable: CompositeDisposable)
    : PageKeyedDataSource<Long, Movie>() {
    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Long, Movie>) {

    }

    val networkState = MutableLiveData<NetworkState>()

    val initialLoad = MutableLiveData<NetworkState>()

    /**
     * Keep Completable reference for the retry event
     */
    private var retryCompletable: Completable? = null

    private var totalPage :Int? = null


    fun retry() {
        if (retryCompletable != null) {
            compositeDisposable.add(retryCompletable!!
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ }, { throwable -> Log.e(TAG, throwable.message) }))
        }
    }

    override fun loadInitial(params: LoadInitialParams<Long>, callback: LoadInitialCallback<Long, Movie>) {
        // update network states.
        // we also provide an initial load state to the listeners so that the UI can know when the
        // very first list is loaded.
        networkState.postValue(NetworkState.LOADING)
        initialLoad.postValue(NetworkState.LOADING)

        Log.i(TAG, "load init")

        //get the initial users from the api

        compositeDisposable.add(dataService.getPopularMovie(1).subscribe({ users ->
            // clear retry since last request succeeded
            setRetry(null)
            Log.i(TAG, "load init success ${users.movie.size}")
            networkState.postValue(NetworkState.LOADED)
            initialLoad.postValue(NetworkState.LOADED)
            callback.onResult(users.movie, null, 2L)
        }, { throwable ->
            // keep a Completable for future retry
            setRetry(Action { loadInitial(params, callback) })
            val error = NetworkState.error(throwable.message)
            // publish the error
            networkState.postValue(error)
            initialLoad.postValue(error)
        }))
    }

    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Long, Movie>) {
        // set network value to loading.
        Log.i(TAG, "load after")



        networkState.postValue(NetworkState.LOADING)
        compositeDisposable.add(dataService.getPopularMovie(params.key.toInt()).subscribe({ users ->
            // clear retry since last request succeeded
            setRetry(null)
            networkState.postValue(NetworkState.LOADED)
            callback.onResult(users.movie, (params.key + 1))
        }, { throwable ->
            // keep a Completable for future retry
            setRetry(Action { loadAfter(params, callback) })
            // publish the error
            networkState.postValue(NetworkState.error(throwable.message))
        }))
    }


    private fun setRetry(action: Action?) {
        if (action == null) {
            this.retryCompletable = null
        } else {
            this.retryCompletable = Completable.fromAction(action)
        }
    }

}