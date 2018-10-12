package com.heinhtet.deevd.splash.network

import android.content.Context
import com.heinhtet.deevd.splash.api.ApiService

/**
 * Created by heinhtet on 1/28/18.
 */
class ProvideApiService {
    lateinit var retrofit: ProvideRetrofit

    fun createApiService(context: Context): ApiService {
        retrofit = ProvideRetrofit()
        return ProvideRetrofit().RetrofitBuilder(context).create(ApiService::class.java)
    }
}