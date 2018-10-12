package com.heinhtet.deevd.splash.api

import com.heinhtet.deevd.splash.model.response.GithubUser
import com.heinhtet.deevd.splash.model.response.MovieResult
import com.heinhtet.deevd.splash.model.response.OAuthModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.POST
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


    @POST("oauth/token")
    fun oAuth(@Query("client_id") clientId: String,
              @Query("client_secret") clientSecret: String,
              @Query("redirect_uri") redirectUri: String,
              @Query("code") code: String,
              @Query("grant_type") grantType: String): Single<OAuthModel>
}