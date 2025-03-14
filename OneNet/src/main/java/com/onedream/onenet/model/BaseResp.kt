package com.onedream.onenet.model

import com.onedream.onenet.model.exception.ApiException

open class BaseResp<T>(
    var code: Int = -1,
    var msg: String = "",
    var data: T
) {
    fun apiDataAndMsg(): Pair<T, String> {
        if (isSuccess()) {
            return Pair(data, msg)
        } else {
            throw ApiException(code, msg)
        }
    }

    fun apiData(): T {
        if (isSuccess()) {
            return data
        } else {
            throw ApiException(code, msg)
        }
    }

    fun apiResp(): BaseResp<T> {
        if (isSuccess()) {
            return this
        } else {
            throw ApiException(code, msg)
        }
    }

    private fun isSuccess(): Boolean {
        return ApiCode.isSuccess(code)
    }
}