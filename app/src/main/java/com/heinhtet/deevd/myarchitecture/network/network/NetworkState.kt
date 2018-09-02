package com.heinhtet.deevd.myarchitecture.network.network

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData


/**
 * Created by Hein Htet on 8/21/18.
 */

enum class Status {
    RUNNING,
    SUCCESS,
    FAILED,
    HAHA
}

@Suppress("DataClassPrivateConstructor")
data class NetworkState private constructor(
        val status: Status,
        val message: String? = null,
        val HAHA: MutableLiveData<String>? = null
) {
    companion object {
        val LOADED = NetworkState(Status.SUCCESS)
        val LOADING = NetworkState(Status.RUNNING)
        fun error(msg: String?) = NetworkState(Status.FAILED, msg)
        fun haha(msg: String) = NetworkState(Status.HAHA, msg)

    }
}
