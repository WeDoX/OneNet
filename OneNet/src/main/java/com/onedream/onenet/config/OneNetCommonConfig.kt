package com.onedream.onenet.config

import android.content.Context
import com.onedream.onenet.handler.OneNetLaunchExceptionHandler
import com.onedream.onenet.handler.OneNetLaunchExceptionHandlerImp
import com.onedream.onenet.interceptor.log.Logger
import com.onedream.onenet.interceptor.log.LoggingInterceptor

/**
 *@author chenguijian
 *@since 2025/3/14
 */
abstract class OneNetCommonConfig(
    private val context: Context,
    private val isDebug: Boolean
) : OneNetConfig {

    override fun launchExceptionHandler(): OneNetLaunchExceptionHandler {
        return OneNetLaunchExceptionHandlerImp(context)
    }

    override fun loggingInterceptor(): LoggingInterceptor? {
        if (!isDebug) {//默认不是debug,不添加日志打印拦截器
            return null
        }
        val logging = LoggingInterceptor(Logger())
        logging.setLevel(LoggingInterceptor.Level.BODY)
        return logging
    }
}