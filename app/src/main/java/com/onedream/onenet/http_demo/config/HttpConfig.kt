package com.onedream.onenet.http_demo.config

import android.content.Context
import com.onedream.onenet.config.OneNetCommonConfig
import com.onedream.onenet.interceptor.HeaderInterceptor.HeaderProvider

/**
 *@author chenguijian
 *@since 2025/3/14
 */
class HttpConfig(context: Context) : OneNetCommonConfig(context, isDebug = true) {
    override fun baseUrl(): String {
        return HttpApiConstant.BASE_URL
    }

    fun headerProvider(): HeaderInterceptor.HeaderProvider{
        return object : HeaderInterceptor.HeaderProvider{
            override fun getHeaders(): MutableMap<String, String> {
                val header = HashMap<String, String>()
                header["app"] = "true"
                return header
            }
        }
    }
}