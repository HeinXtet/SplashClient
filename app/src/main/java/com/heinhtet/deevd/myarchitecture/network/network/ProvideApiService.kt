package com.heinhtet.deevd.myarchitecture.network.network

import android.content.Context
import com.heinhtet.deevd.myarchitecture.network.api.ApiService

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