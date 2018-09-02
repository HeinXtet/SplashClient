package com.heinhtet.deevd.myarchitecture.network.data

import android.content.Context
import com.heinhtet.deevd.myarchitecture.network.api.ApiService
import com.heinhtet.deevd.myarchitecture.network.base.baseutils.API_KEY
import com.heinhtet.deevd.myarchitecture.network.model.response.GithubUser
import com.heinhtet.deevd.myarchitecture.network.model.response.MovieResult
import com.heinhtet.deevd.myarchitecture.network.network.ProvideApiService
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

}