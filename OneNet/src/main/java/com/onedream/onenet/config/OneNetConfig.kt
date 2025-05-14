package com.onedream.onenet.config

import com.onedream.onenet.handler.OneNetLaunchExceptionHandler
import com.onedream.onenet.interceptor.HeaderInterceptor.HeaderProvider
import com.onedream.onenet.interceptor.log.LoggingInterceptor

/**
 *@author chenguijian
 *@since 2025/3/13
 */
interface OneNetConfig {

    fun baseUrl(): String

    fun headerProvider(): HeaderInterceptor.HeaderProvider

    fun launchExceptionHandler() : OneNetLaunchExceptionHandler

    fun loggingInterceptor() : LoggingInterceptor?
}