package com.kriswantoro.indramayu.util.retrofit.network.request

import com.kriswantoro.indramayu.util.retrofit.model.CheckNikModel
import com.kriswantoro.indramayu.util.retrofit.network.response.BaseResponse
import retrofit2.Call
import retrofit2.http.*

interface CheckNikRequest {

    @Headers("Content-Type: application/json")
    @FormUrlEncoded
    @POST("slim/v1")
    fun checkNik(
        @Query("apicall") apicall: String,
        @Field("nik") nik: String
    ): Call<BaseResponse<CheckNikModel>>
}