package com.kriswantoro.indramayu.util.retrofit.network

import com.kriswantoro.indramayu.util.Constant.API_BASE_PATH
import com.kriswantoro.indramayu.util.retrofit.RetrofitClient
import com.kriswantoro.indramayu.util.retrofit.network.request.CheckNikRequest
import com.kriswantoro.indramayu.util.retrofit.network.request.NomorPanggilanRequest
import com.kriswantoro.indramayu.util.retrofit.network.request.PengaduanRequest
import com.kriswantoro.indramayu.util.retrofit.network.request.SaranRequest

object BaseService {

    fun getKategoriPengaduanService(): PengaduanRequest{
        return RetrofitClient.getClient(API_BASE_PATH)!!.create(PengaduanRequest::class.java)
    }

    fun getKategoriDinasService(): SaranRequest{
        return RetrofitClient.getClient(API_BASE_PATH)!!.create(SaranRequest::class.java)
    }

    fun checkNik(): CheckNikRequest{
        return RetrofitClient.getClient(API_BASE_PATH)!!.create(CheckNikRequest::class.java)
    }

    fun getNomorPanggilan(): NomorPanggilanRequest{
        return RetrofitClient.getClient(API_BASE_PATH)!!.create(NomorPanggilanRequest::class.java)
    }
}