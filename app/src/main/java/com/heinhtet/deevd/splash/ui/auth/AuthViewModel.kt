package com.heinhtet.deevd.splash.ui.auth

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.heinhtet.deevd.splash.base.baseutils.BASE_URL
import com.heinhtet.deevd.splash.data.DataService
import com.heinhtet.deevd.splash.model.response.OAuthModel
import com.heinhtet.deevd.splash.model.response.UserModel
import com.heinhtet.deevd.splash.network.ApiManager
import com.heinhtet.deevd.splash.ui.main.paginate.TAG
import com.heinhtet.deevd.splash.utils.log.L
import io.reactivex.Single

/**
 * Created by Hein Htet on 10/12/18.
 */
class AuthViewModel(private val dataService: DataService) : ViewModel() {

    var oauth = MutableLiveData<OAuthModel>()
    var userModel = MutableLiveData<UserModel>()
    private val TAG = "AuthViewModel "


    fun oAuth(code: String) {
        ApiManager.request(dataService.oAuth(code), loading = {
        }, success = {
            oauth.postValue(it)
        }, error = { type: Int, throwable: Throwable ->
            L.i(TAG, " Error ${throwable.localizedMessage} ")
        })
    }

    fun getMe(token: String) {
        ApiManager.request(dataService.getMe(token), success = {
            L.i(TAG, " success me $it")
            userModel.postValue(it)
        }, loading = {

        }, error = { type: Int, throwable: Throwable ->
            L.i(TAG, "get me error ${throwable.localizedMessage}")
        })
    }


}