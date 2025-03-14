package com.onedream.onenet.handler

/**
 *@author chenguijian
 *@since 2025/3/14
 */
interface OneNetLaunchExceptionHandler {
    fun onError(e: Exception): Exception
}