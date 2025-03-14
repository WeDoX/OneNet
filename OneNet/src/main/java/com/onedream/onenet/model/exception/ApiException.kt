package com.onedream.onenet.model.exception

class ApiException(var code: Int, override var message: String) : RuntimeException()