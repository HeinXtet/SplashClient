package com.heinhtet.deevd.myarchitecture.network.api

import com.heinhtet.deevd.myarchitecture.network.model.response.GithubUser
import com.heinhtet.deevd.myarchitecture.network.model.response.MovieResult
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Hein Htet on 8/21/18.
 */
interface ApiService {
    @GET("popular")
    fun getPopularList(@Query("api_key") apikey: String,
                       @Query("page") page: Int): Single<MovieResult>

    @GET("users")
    fun getUsers(@Query("since") id: Int): Single<List<GithubUser>>

}