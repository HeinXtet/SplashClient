package com.heinhtet.deevd.splash.data

import android.content.Context
import com.heinhtet.deevd.splash.api.ApiService
import com.heinhtet.deevd.splash.base.baseutils.ACCESS_KEY
import com.heinhtet.deevd.splash.base.baseutils.API_KEY
import com.heinhtet.deevd.splash.base.baseutils.SECRET_KEY
import com.heinhtet.deevd.splash.model.response.GithubUser
import com.heinhtet.deevd.splash.model.response.MovieResult
import com.heinhtet.deevd.splash.model.response.OAuthModel
import com.heinhtet.deevd.splash.network.ProvideApiService
import io.reactivex.Single

/**
 * Created by Hein Htet on 8/21/18.
 */

class DataService(var context: Context) {
    private var apiService: ApiService

    init {
        var provideApiService = ProvideApiService()
        apiService = provideApiService.createApiService(context)
    }

    fun getPopularMovie(page: Int): Single<MovieResult> {
        return apiService.getPopularList(API_KEY, page)
    }

    fun getGithubUser(): Single<List<GithubUser>> {
        return apiService.getUsers(1)
    }

    fun oAuth(code: String): Single<OAuthModel> {
        return apiService.oAuth(ACCESS_KEY, SECRET_KEY, "https://www.google.com", code, "authorization_code")
    }
}