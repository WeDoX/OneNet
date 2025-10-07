package com.onedream.onenet.utils


import com.onedream.onenet.OneNet
import com.onedream.onenet.interceptor.HeaderInterceptor
import com.onedream.onenet.interceptor.log.Logger
import com.onedream.onenet.interceptor.log.LoggingInterceptor
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitFactory {

    fun <T> create(clazz: Class<T>): T {
        return retrofit.create(clazz)
    }


    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(OneNet.requireConfig().baseUrl())
            .addConverterFactory(GsonConverterFactory.create())
            .client(initOkHttpClient())
            .build()
    }


    private fun initOkHttpClient(): OkHttpClient {
        val sslParams: HttpsUtils.SSLParams = HttpsUtils.getSslSocketFactory()
        //
        return OkHttpClient.Builder()
            .addInterceptor(HeaderInterceptor(OneNet.requireConfig().headerProvider())).apply {
                val loggingInterceptor = OneNet.requireConfig().loggingInterceptor()
                loggingInterceptor?.let {
                    addInterceptor(it)
                }
            }
            .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
            .connectTimeout(20L, TimeUnit.SECONDS)
            .readTimeout(60L, TimeUnit.SECONDS)
            .writeTimeout(20L, TimeUnit.SECONDS)
            .connectionPool(ConnectionPool(8, 15, TimeUnit.SECONDS))
            .build()
    }
}