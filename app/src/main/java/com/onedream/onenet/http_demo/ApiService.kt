package com.onedream.onenet.http_demo

import com.onedream.onenet.http_demo.resp.OneNetTestGetResp
import com.onedream.onenet.model.BaseResp
import retrofit2.http.GET
import retrofit2.http.Query

/**
 *@author chenguijian
 *@since 2025/3/14
 */
interface ApiService {
    @GET("/other/one_net_test.php")
    suspend fun testGet(@Query("submit_content") submit_content:String): BaseResp<OneNetTestGetResp>
}