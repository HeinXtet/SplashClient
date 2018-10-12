package com.heinhtet.deevd.splash.model.response

import com.squareup.moshi.Json

/**
 * Created by Hein Htet on 10/12/18.
 */
data class OAuthModel(
        @Json(name = "access_token") var accessToken: String,
        @Json(name = "token_type") var tokenType: String,
        @Json(name = "scope") var scope: String,
        @Json(name = "created_at") var created: Any
)