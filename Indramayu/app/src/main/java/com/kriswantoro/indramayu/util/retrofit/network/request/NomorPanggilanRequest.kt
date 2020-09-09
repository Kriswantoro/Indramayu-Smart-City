package com.kriswantoro.indramayu.util.retrofit.network.request

import com.kriswantoro.indramayu.util.retrofit.model.KategoriDinasModel
import com.kriswantoro.indramayu.util.retrofit.model.NomorPanggilanModel
import com.kriswantoro.indramayu.util.retrofit.network.response.BaseResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface NomorPanggilanRequest {

    @Headers("Content-Type: application/json")
    @GET("slim/v1")
    fun getNomorPanggilan(
        @Query("apicall") apicall: String
    ): Call<BaseResponse<NomorPanggilanModel>>
}