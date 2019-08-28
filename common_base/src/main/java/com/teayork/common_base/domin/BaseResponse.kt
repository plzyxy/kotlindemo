package com.teayork.common_base.domin

data class BaseResponse<T>(val errorCode: Int, val errorMsg: String, val data: T)