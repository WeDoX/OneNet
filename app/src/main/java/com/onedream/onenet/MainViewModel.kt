package com.onedream.onenet

import com.onedream.event_livedata.EventLiveData
import com.onedream.event_livedata.MutableEventLiveData
import com.onedream.onenet.base.BaseViewModel
import com.onedream.onenet.http_demo.HttpDataRepository
import com.onedream.onenet.http_demo.resp.OneNetTestGetResp

/**
 *@author chenguijian
 *@since 2025/3/14
 */
class MainViewModel : BaseViewModel() {
    private val _testGetSuccess = MutableEventLiveData<Pair<OneNetTestGetResp, String>>()
    val testGetSuccess: EventLiveData<Pair<OneNetTestGetResp, String>>
        get() = _testGetSuccess

    fun requestTestGet(submit_content: String) {
        launchAsyncDataAndMsg(block = {
            HttpDataRepository.testGet(submit_content)
        }, success = {
            _testGetSuccess.postValue(it)
        }, error = {
            val errMsg = if (it.message == null) "" else it.message!!
            sendLaunchErrorResp(errMsg)
        })
    }

}