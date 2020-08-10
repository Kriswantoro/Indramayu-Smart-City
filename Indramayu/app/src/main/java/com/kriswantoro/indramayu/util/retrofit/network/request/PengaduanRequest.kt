package com.kriswantoro.indramayu.util.retrofit.network.request

import com.kriswantoro.indramayu.ui.beranda.buat_pengaduan.KategoriPengaduanModel
import com.kriswantoro.indramayu.util.retrofit.network.response.BaseResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface PengaduanRequest {

    @Headers("Content-Type: application/json")
    @GET("slim/v1")
    fun getKategoriPengaduan(
        @Query("apicall") apicall: String
    ): Call<BaseResponse<KategoriPengaduanModel>>
}