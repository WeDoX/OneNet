package com.onedream.onenet.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.onedream.event_livedata.EventLiveData
import com.onedream.event_livedata.MutableEventLiveData
import com.onedream.onenet.OneNet
import com.onedream.onenet.model.BaseResp
import kotlinx.coroutines.*

typealias Callback<T> = suspend (T) -> Unit
typealias Block<T> = suspend () -> T
typealias Error = suspend (e: Exception) -> Unit
typealias Cancel = suspend (e: Exception) -> Unit

open class BaseViewModel : ViewModel() {
    private val mLaunchErrorResp =
        MutableEventLiveData<String>()
    val launchErrorResp: EventLiveData<String>
        get() = mLaunchErrorResp

    protected fun sendLaunchErrorResp(errMsg:String){
        mLaunchErrorResp.postValue(errMsg)
    }


    private fun launch(block: Block<Unit>, error: Error? = null, cancel: Cancel? = null): Job {
        return viewModelScope.launch {
            try {
                block.invoke()
            } catch (e: Exception) {
                e.printStackTrace()
                when (e) {
                    is CancellationException -> {
                        cancel?.invoke(e)
                    }

                    else -> {
                        val handlerException =
                            OneNet.requireConfig().launchExceptionHandler().onError(e) ?: return@launch
                        error?.invoke(handlerException)
                    }
                }
            }
        }
    }

    private fun <T> async(block: Block<T>): Deferred<T> {
        return viewModelScope.async(Dispatchers.IO) { block.invoke() }
    }

    protected fun <T> launchAsyncDataAndMsg(
        block: Block<BaseResp<T>>,
        success: Callback<Pair<T, String>>,
        error: Error? = null,
        cancel: Cancel? = null
    ): Job {
        return launch(
            block = {
                val dataDeferred = async { block.invoke().apiDataAndMsg() }
                success.invoke(dataDeferred.await())
            },
            error = error,
            cancel = cancel
        )
    }

    protected fun <T> launchAsyncData(
        block: Block<BaseResp<T>>,
        success: Callback<T>,
        error: Error? = null,
        cancel: Cancel? = null
    ): Job {
        return launch(
            block = {
                val dataDeferred = async { block.invoke().apiData() }
                success.invoke(dataDeferred.await())
            },
            error = error,
            cancel = cancel
        )
    }


    protected fun <T> launchAsyncResp(
        block: Block<BaseResp<T>>,
        success: Callback<BaseResp<T>>,
        error: Error? = null,
        cancel: Cancel? = null
    ): Job {
        return launch(
            block = {
                val dataDeferred = async { block.invoke().apiResp() }
                success.invoke(dataDeferred.await())
            },
            error = error,
            cancel = cancel
        )
    }

    protected fun <T> launchAsyncRespAnyCode(
        block: Block<BaseResp<T>>,
        success: Callback<BaseResp<T>>,
        error: Error? = null,
        cancel: Cancel? = null
    ): Job {
        return launch(
            block = {
                val dataDeferred = async { block.invoke() }
                success.invoke(dataDeferred.await())
            },
            error = error,
            cancel = cancel
        )
    }


    protected fun cancelJob(job: Job?) {
        if (job != null && job.isActive && !job.isCompleted && !job.isCancelled) {
            job.cancel()
        }
    }

}