package com.heinhtet.deevd.splash.ui.home

import android.arch.lifecycle.MutableLiveData
import com.heinhtet.deevd.splash.model.response.GithubUser

/**
 * Created by Hein Htet on 8/22/18.
 */
data class DataResult(
        var gitHubUserList: MutableLiveData<List<GithubUser>>,
        var state: MutableLiveData<State>
)

data class State(
        var state: String,
        var message: String
)