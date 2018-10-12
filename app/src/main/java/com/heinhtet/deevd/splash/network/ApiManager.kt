package com.heinhtet.deevd.splash.network

import com.heinhtet.deevd.splash.base.baseutils.RxThread
import io.reactivex.Single

/**
 * Created by Hein Htet on 9/8/18.
 */
object ApiManager {
    fun <T> request(api: Single<T>, loading: (loading: Boolean) -> Unit,
                    success: (T) -> Unit,
                    error: (type: Int, message: Throwable) -> Unit) {
        loading(true)
        api.compose(RxThread.applyAsync())
                .doAfterSuccess {
                    loading(false)
                    success(it)
                }
                .doOnError {
                    loading(false)
                    error(9,it)
                }
                .subscribe()
    }
}