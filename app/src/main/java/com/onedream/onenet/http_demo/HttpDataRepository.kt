package com.onedream.onenet.http_demo

import com.onedream.onenet.http_demo.resp.OneNetTestGetResp
import com.onedream.onenet.model.BaseResp
import com.onedream.onenet.utils.RetrofitFactory

/**
 *@author chenguijian
 *@since 2025/3/14
 */
object HttpDataRepository {

    private val apiService: ApiService by lazy {
        RetrofitFactory.create(
            ApiService::class.java
        )
    }

    suspend fun testGet(submit_content: String): BaseResp<OneNetTestGetResp> {
        return apiService.testGet(submit_content)
    }
}