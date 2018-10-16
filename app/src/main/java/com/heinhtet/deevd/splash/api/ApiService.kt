package com.heinhtet.deevd.splash.api

import com.heinhtet.deevd.splash.model.response.*
import io.reactivex.Single
import retrofit2.http.*

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
    fun oAuth(@Url url: String, @Query("client_id") clientId: String,
              @Query("client_secret") clientSecret: String,
              @Query("redirect_uri") redirectUri: String,
              @Query("code") code: String,
              @Query("grant_type") grantType: String): Single<OAuthModel>

    @POST
    fun oAuth(@Url url: String): Single<OAuthModel>

    @GET("me")
    fun getMe(@Header("Authorization") auth: String): Single<UserModel>

    @GET("photos")
    fun getPhotos(@Header("Authorization") auth: String,
                  @Query("page") page: Int,
                  @Query("per_page") perPage: Int,
                  @Query("order_by") orderBy: String): Single<List<PhotoModel>>
}