package com.onedream.onenet.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.MediaType
import okhttp3.RequestBody

fun String.jsonStrToRequestBody(): RequestBody {
    return RequestBody.create(MediaType.parse("application/json;charset=utf-8"), this)
}

fun MutableMap<String, Any>.mapToRequestBody(): RequestBody {
    val gson = GsonBuilder().enableComplexMapKeySerialization().create()
    val jsonStr = gson.toJson(this)
    return jsonStr.jsonStrToRequestBody()
}

fun Any.objectToRequestBody(): RequestBody {
    val jsonStr = Gson().toJson(this)
    return jsonStr.jsonStrToRequestBody()
}