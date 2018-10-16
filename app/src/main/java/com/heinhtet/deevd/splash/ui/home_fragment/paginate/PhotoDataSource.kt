package com.heinhtet.deevd.splash.ui.home_fragment.paginate

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.PageKeyedDataSource
import android.arch.paging.PagedList
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.heinhtet.deevd.splash.data.DataService
import com.heinhtet.deevd.splash.data.PrefHelper
import com.heinhtet.deevd.splash.model.response.PhotoModel
import com.heinhtet.deevd.splash.network.NetworkState
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers

/**
 * Created by Hein Htet on 10/14/18.
 */
private val TAG = "PhotoDataSoruce"

class PhotoDataSource(private val compositeDisposable: CompositeDisposable,
                      private val dataService: DataService) : PageKeyedDataSource<Long, PhotoModel>() {
    override fun loadInitial(params: LoadInitialParams<Long>, callback: LoadInitialCallback<Long, PhotoModel>) {

        // very first list is loaded.
        networkState.postValue(NetworkState.LOADING)
        initialLoad.postValue(NetworkState.LOADING)

        Log.i(TAG, "load init")

        //get the initial users from the api

        if (PrefHelper.instance().getPhotoModel() != null) {
            val strList = PrefHelper.instance().getPhotoModel() as String
            val type = object : TypeToken<PagedList<PhotoModel>>() {
            }.type
            val list = Gson().fromJson(strList, type) as ArrayList<PhotoModel>
            networkState.postValue(NetworkState.LOADED)
            initialLoad.postValue(NetworkState.LOADED)
            callback.onResult(list, null, null)

            return
        }

        compositeDisposable.add(dataService.getPhotos(PrefHelper.instance().getOAuthModel().accessToken, 1).subscribe({ photos ->
            // clear retry since last request succeeded
            setRetry(null)
            Log.i(TAG, "load init success ${photos.size}")
            networkState.postValue(NetworkState.LOADED)
            initialLoad.postValue(NetworkState.LOADED)
            callback.onResult(photos, null, 2L)
        }, { throwable ->
            // keep a Completable for future retry
            setRetry(Action { loadInitial(params, callback) })
            val error = NetworkState.error(throwable.message, throwable)
            // publish the error
            networkState.postValue(error)
            initialLoad.postValue(error)
        }))
    }

    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Long, PhotoModel>) {
        Log.i(TAG, "load after")
        networkState.postValue(NetworkState.LOADING)
        compositeDisposable.add(dataService.getPhotos(PrefHelper.instance().getOAuthModel().accessToken, params.key.toInt()).subscribe({ photos ->
            // clear retry since last request succeeded
            setRetry(null)
            networkState.postValue(NetworkState.LOADED)
            callback.onResult(photos, (params.key + 1))
        }, { throwable ->
            // keep a Completable for future retry
            setRetry(Action { loadAfter(params, callback) })
            // publish the error
            networkState.postValue(NetworkState.error(throwable.message, throwable))
        }))
    }

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Long, PhotoModel>) {

    }

    val networkState = MutableLiveData<NetworkState>()

    val initialLoad = MutableLiveData<NetworkState>()

    fun retry() {
        if (retryCompletable != null) {
            compositeDisposable.add(retryCompletable!!
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ }, { throwable -> Log.e(TAG, throwable.message) }))
        }
    }

    /**
     * Keep Completable reference for the retry event
     */
    private var retryCompletable: Completable? = null

    private var totalPage: Int? = null


    private fun setRetry(action: Action?) {
        if (action == null) {
            this.retryCompletable = null
        } else {
            this.retryCompletable = Completable.fromAction(action)
        }
    }

}