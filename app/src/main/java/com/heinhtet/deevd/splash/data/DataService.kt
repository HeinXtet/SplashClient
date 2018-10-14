package com.heinhtet.deevd.splash.data

import android.content.Context
import com.heinhtet.deevd.splash.BuildConfig
import com.heinhtet.deevd.splash.api.ApiService
import com.heinhtet.deevd.splash.base.baseutils.*
import com.heinhtet.deevd.splash.model.response.GithubUser
import com.heinhtet.deevd.splash.model.response.MovieResult
import com.heinhtet.deevd.splash.model.response.OAuthModel
import com.heinhtet.deevd.splash.model.response.UserModel
import com.heinhtet.deevd.splash.network.ProvideApiService
import io.reactivex.Single

/**
 * Created by Hein Htet on 8/21/18.
 */

class DataService(var context: Context) {
    private val BEARER = "Bearer "
    private var apiService: ApiService = ProvideApiService().createApiService(context)

    fun getPopularMovie(page: Int): Single<MovieResult> {
        return apiService.getPopularList(API_KEY, page)
    }

    fun getGithubUser(): Single<List<GithubUser>> {
        return apiService.getUsers(1)
    }

    fun oAuth(code: String): Single<OAuthModel> {
        val OAUTH = "https://unsplash.com/oauth/token?client_id=${BuildConfig.AccessKey}&client_secret=${BuildConfig.SecretKey}&redirect_uri=$REDIRTECT_URI&code=$code&grant_type=authorization_code"
        return apiService.oAuth(OAUTH)
    }

    fun getMe(token: String): Single<UserModel> {
        return apiService.getMe(BEARER + token)
    }
}