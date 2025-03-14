package com.onedream.onenet.model

object ApiCode {
    private const val CODE_SUCCESS = 200

    @JvmStatic
    fun isSuccess(code: Int) : Boolean{
        return code == CODE_SUCCESS
    }
}