package com.heinhtet.deevd.splash.network

import com.heinhtet.deevd.splash.base.baseutils.RxThread
import io.reactivex.Single

/**
 * Created by Hein Htet on 9/8/18.
 */


object ApiManager {
    fun <T> request(api: Single<T>,
                    loading: (loading: NetworkState) -> Unit,
                    success: (T) -> Unit,
                    error: (type: Int, message: Throwable) -> Unit) {
        loading(NetworkState.LOADING)
        api.compose(RxThread.applyAsync())
                .doAfterSuccess {
                    loading(NetworkState.LOADED)
                    success(it)
                }
                .doOnError {
                    loading(NetworkState.LOADED)
                    error(9, it)
                }
                .subscribe()
    }

}