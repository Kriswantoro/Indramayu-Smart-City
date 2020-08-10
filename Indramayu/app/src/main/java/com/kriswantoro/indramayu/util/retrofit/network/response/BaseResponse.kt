package com.kriswantoro.indramayu.util.retrofit.network.response

import com.google.gson.annotations.SerializedName

class BaseResponse<T>(
    @SerializedName("status") var status: String = "",
    @SerializedName("code") var code: Int? = null,
    @SerializedName("response") var response: Array<T>? = null
)