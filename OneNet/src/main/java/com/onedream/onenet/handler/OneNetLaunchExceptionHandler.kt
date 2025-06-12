package com.onedream.onenet.handler

/**
 *@author chenguijian
 *@since 2025/3/14
 */
interface OneNetLaunchExceptionHandler {
    //返回null时，回调函数不再继续处理该异常
    //使用场景：
    //登录信息失效时，实现退出登录，这时就没必要再在回调函数里处理了，返回null
    fun onError(e: Exception): Exception?
}