package com.onedream.onenet

import android.app.Application
import com.onedream.onenet.http_demo.config.HttpConfig

/**
 *@author chenguijian
 *@since 2025/3/14
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        //
        OneNet.init(HttpConfig(this.applicationContext))
    }
}