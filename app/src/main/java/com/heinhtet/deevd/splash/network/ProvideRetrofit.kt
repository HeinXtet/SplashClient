package com.heinhtet.deevd.splash.network

import android.content.Context
import com.heinhtet.deevd.splash.BuildConfig
import com.heinhtet.deevd.splash.base.baseutils.BASE_URL
import com.readystatesoftware.chuck.ChuckInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by heinhtet on 1/28/18.
 */
class ProvideRetrofit {
    private fun getOkHttpClient(context: Context): OkHttpClient {
        val client = OkHttpClient.Builder()


        if (BuildConfig.L_ENABLE) {
            client.addInterceptor(ChuckInterceptor(context))
        }
        client.connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
        return client.build()
    }


    private fun getMoshiBuilder(): Moshi {
        return Moshi
                .Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
    }

    fun RetrofitBuilder(context: Context): Retrofit {
        return Retrofit
                .Builder()
                .baseUrl(BASE_URL)
                .client(getOkHttpClient(context))
                .addConverterFactory(MoshiConverterFactory.create(getMoshiBuilder()).asLenient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }
}