package com.heinhtet.deevd.splash.ui.auth

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.heinhtet.deevd.splash.data.DataService
import com.heinhtet.deevd.splash.model.response.OAuthModel
import com.heinhtet.deevd.splash.network.ApiManager
import com.heinhtet.deevd.splash.ui.main.paginate.TAG
import com.heinhtet.deevd.splash.utils.log.L
import io.reactivex.Single

/**
 * Created by Hein Htet on 10/12/18.
 */
class AuthViewModel(var dataService: DataService) : ViewModel() {

    var oauth = MutableLiveData<OAuthModel>()
    private val TAG = "AuthViewModel "


    fun oAuth(code: String) {
        ApiManager.request(dataService.oAuth(code), loading = {

        }, success = {
            oauth.postValue(it)
        }, error = { type: Int, throwable: Throwable ->
            L.i(TAG, " Error ${throwable.localizedMessage} ")
        })
    }


}