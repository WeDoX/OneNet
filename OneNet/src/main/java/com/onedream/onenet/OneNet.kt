package com.onedream.onenet

import com.onedream.onenet.config.OneNetConfig

/**
 *@author chenguijian
 *@since 2025/3/13
 */
object OneNet {
    private lateinit var mConfig: OneNetConfig

    fun init(config: OneNetConfig) {
        mConfig = config
    }

    internal fun requireConfig(): OneNetConfig {
        if (!::mConfig.isInitialized) {
            throw Exception("OneNet don't init")
        }
        return mConfig
    }
}