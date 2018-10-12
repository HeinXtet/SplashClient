package com.heinhtet.deevd.splash.model.response

import com.squareup.moshi.Json

/**
 * Created by Hein Htet on 10/13/18.
 */
data class UserModel(
        @Json(name = "username") var userName: String,
        @Json(name = "email") var email: String
)