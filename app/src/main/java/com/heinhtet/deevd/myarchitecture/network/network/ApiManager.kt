package com.heinhtet.deevd.myarchitecture.network.network

import android.content.Context
import com.heinhtet.deevd.myarchitecture.network.base.baseutils.RxThread
import com.heinhtet.deevd.myarchitecture.network.data.DataService
import io.reactivex.Single

/**
 * Created by Hein Htet on 9/8/18.
 */
object ApiManager {
    fun <T> request(api: Single<T>, loading: (loading: Boolean) -> Unit,
                    success: (T) -> Unit,
                    error: (type: Int, message: String?) -> Unit) {
        loading(true)
        api.compose(RxThread.applyAsync())
                .doAfterSuccess {
                    loading(false)
                    success(it)
                }
                .doOnError {

                    loading(false)

                }
                .subscribe()

    }
}