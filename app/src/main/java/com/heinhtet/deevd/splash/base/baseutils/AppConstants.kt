package com.heinhtet.deevd.splash.base.baseutils

import com.heinhtet.deevd.splash.BuildConfig

/**
 * Created by Hein Htet on 8/21/18.
 */

var BASE_URL = "https://api.unsplash.com"
val API_KEY = "961277648f1e28c74788bade62b3b24c"
val BASE_IV_URL = "https://unsplash.com/"
val AUTH_REGEX = "https:\\/\\/(.*)\\/?code=(.*)".toRegex()
val REDIRTECT_URI = "https://heinxtet.github.io/github.splash/"
val AUTH_URL = "https://unsplash.com/oauth/authorize?client_id=${BuildConfig.AccessKey}&redirect_uri=$REDIRTECT_URI&response_type=code&scope=public+read_user"
