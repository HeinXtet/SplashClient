package com.heinhtet.deevd.myarchitecture.network.ui.home

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.os.Handler
import com.heinhtet.deevd.myarchitecture.network.data.DataService
import com.heinhtet.deevd.myarchitecture.network.base.baseutils.RxThread
import com.heinhtet.deevd.myarchitecture.network.model.response.GithubUser
import com.heinhtet.deevd.myarchitecture.network.network.NetworkState
import io.reactivex.rxkotlin.subscribeBy

/**
 * Created by Hein Htet on 8/22/18.
 */
class HomeViewModel(var dataService: DataService) : ViewModel() {


    var gitHubUserList = MutableLiveData<List<GithubUser>>()
    var networkStates = MutableLiveData<NetworkState>()

    var isMyanmarLanguage = MutableLiveData<Boolean>()


    fun getNetworkState(): MutableLiveData<NetworkState> {
        return networkStates
    }

    fun getGithubUser(): MutableLiveData<List<GithubUser>> {
        return gitHubUserList
    }


    fun testingObserve(): MutableLiveData<Boolean> {
        return isMyanmarLanguage
    }

    init {
        fetchUser()
    }


    private fun fetchUser() {
        var networkError = MutableLiveData<State>()
        networkStates.postValue(NetworkState.haha("HAHA init Changes"))
        isMyanmarLanguage.postValue(false)
        Handler().postDelayed({ isMyanmarLanguage.postValue(true) }, 2000)
        dataService.getGithubUser()
                .compose(RxThread.applyAsync())
                .subscribeBy(onError = {
                    networkStates.postValue(NetworkState.error("${it.localizedMessage}"))
                    networkError.postValue(com.heinhtet.deevd.myarchitecture.network.ui.home.State("DATA ERROR ", it.localizedMessage))

                }, onSuccess = {
                    gitHubUserList.postValue(it)
                })
    }
}