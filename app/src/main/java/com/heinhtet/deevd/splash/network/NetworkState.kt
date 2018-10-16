package com.heinhtet.deevd.splash.network


/**
 * Created by Hein Htet on 8/21/18.
 */

enum class Status {
    RUNNING,
    SUCCESS,
    FAILED
}

@Suppress("DataClassPrivateConstructor")
data class NetworkState private constructor(
        val status: Status,
        val message: String? = null,
        val throwable: Throwable? = null
) {
    companion object {
        val LOADED = NetworkState(Status.SUCCESS)
        val LOADING = NetworkState(Status.RUNNING)
        fun error(msg: String?, throwable: Throwable) = NetworkState(Status.FAILED, msg, throwable)
    }
}
