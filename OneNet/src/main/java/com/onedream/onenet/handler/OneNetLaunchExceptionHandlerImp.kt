package com.onedream.onenet.handler

import android.accounts.NetworkErrorException
import android.content.Context
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.stream.MalformedJsonException
import com.onedream.onenet.R
import com.onedream.onenet.model.exception.ErrorSignatureException
import com.onedream.onenet.model.exception.HttpStatusCodeExceptionResp
import retrofit2.HttpException
import java.io.InterruptedIOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 *@author chenguijian
 *@since 2025/3/14
 */
open class OneNetLaunchExceptionHandlerImp(private val context: Context) : OneNetLaunchExceptionHandler {
    override fun onError(e: Exception): Exception? {
        when (e) {
            is HttpException -> {
                val httpException = e as HttpException
                //不能取两次
                val bodyJsonStr = httpException.response()?.errorBody()?.string()
                //状态码为鉴权码的处理
                if (httpException.code() == 401) {
                    try {
                        val baseResp =
                            Gson().fromJson(bodyJsonStr, HttpStatusCodeExceptionResp::class.java)
                        if (!baseResp.msg.isNullOrEmpty()) {
                            return ErrorSignatureException(baseResp.msg)
                        }
                        if (!baseResp.detail.isNullOrEmpty()) {
                            return ErrorSignatureException(baseResp.detail)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                //其他状态码，先看看是不是json并且msg字段的值不为空
                try {
                    val baseResp =
                        Gson().fromJson(bodyJsonStr, HttpStatusCodeExceptionResp::class.java)
                    if (!baseResp.msg.isNullOrEmpty()) {
                        return Exception(baseResp.msg)
                    }
                    if (!baseResp.detail.isNullOrEmpty()) {
                        return Exception(baseResp.detail)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                //否则，提示【HTTP响应异常，状态码为%s】
                return Exception(
                    String.format(
                        context.getString(R.string.one_net_http_status_code_exception_),
                        httpException.code().toString()
                    )
                )
            }

            is SocketTimeoutException -> {
                return Exception(context.getString(R.string.one_net_http_network_timeout))
            }

            is UnknownHostException, is NetworkErrorException -> {
                return Exception(context.getString(R.string.one_net_http_network_error))
            }

            is MalformedJsonException, is JsonSyntaxException -> {
                return Exception(context.getString(R.string.one_net_http_json_parse_error))
            }

            is InterruptedIOException -> {
                return Exception(context.getString(R.string.one_net_http_network_error))
            }

            is ConnectException -> {
                return Exception(context.getString(R.string.one_net_http_network_error))
            }
        }
        return e
    }
}