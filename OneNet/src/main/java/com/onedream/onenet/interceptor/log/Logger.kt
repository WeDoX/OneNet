package com.onedream.onenet.interceptor.log

import android.util.Log

class Logger : LoggingInterceptor.Logger {

    override fun log(message: String) {
        Log.i("OneNet", "http| $message")
    }
}