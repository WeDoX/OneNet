package com.onedream.onenet.utils

import com.google.gson.GsonBuilder
import okhttp3.MediaType
import okhttp3.RequestBody

object RequestBodyHelper {

    @JvmStatic
    fun createRequestBody(jsonStr: String): RequestBody {
        return RequestBody.create(MediaType.parse("application/json;charset=utf-8"), jsonStr)
    }

    @JvmStatic
    fun createRequestBody(dataParams: Map<*, *>?): RequestBody {
        val jsonStr = mapToJsonStr(dataParams)
        return createRequestBody(jsonStr)
    }

    private fun mapToJsonStr(dataParams: Map<*, *>?): String {
        val dataNotNullParams = dataParams ?: HashMap<Any?, Any?>(16)
        val gson = GsonBuilder().enableComplexMapKeySerialization().create()
        return gson.toJson(dataNotNullParams)
    }
}