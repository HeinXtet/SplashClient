package com.heinhtet.deevd.splash.utils.Auth

import com.heinhtet.deevd.splash.data.PrefHelper
import com.heinhtet.deevd.splash.model.response.UserModel

/**
 * Created by Hein Htet on 10/13/18.
 */
object Auth {

    fun setAuth(userModel: UserModel) {
        PrefHelper.instance().setUserModel(userModel)
        PrefHelper.instance().setAuth(true)
    }

    fun removeAuth() {
        PrefHelper.instance().setAuth(false)
    }

    fun hasAuth() : Boolean = PrefHelper.instance().hasAuth()

    fun getUserModel() : UserModel = PrefHelper.instance().getUserModel()

}