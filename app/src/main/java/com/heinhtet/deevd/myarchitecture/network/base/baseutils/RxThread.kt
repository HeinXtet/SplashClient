package com.heinhtet.deevd.myarchitecture.network.base.baseutils

import io.reactivex.ObservableTransformer
import io.reactivex.SingleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by Hein Htet on 8/22/18.
 */
class RxThread {

    companion object {
        fun <T> applyAsync(): SingleTransformer<T, T> {
            return SingleTransformer {
                it.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
            }
        }

        fun <T> applyAsyncObservable(): ObservableTransformer<T, T> {
            return ObservableTransformer {
                it.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
            }
        }
    }
}

