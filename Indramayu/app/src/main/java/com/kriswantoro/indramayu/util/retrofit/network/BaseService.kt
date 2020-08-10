package com.kriswantoro.indramayu.util.retrofit.network

import com.kriswantoro.indramayu.util.Constant.API_BASE_PATH
import com.kriswantoro.indramayu.util.retrofit.RetrofitClient
import com.kriswantoro.indramayu.util.retrofit.network.request.PengaduanRequest

object BaseService {

    fun getKategoriPengaduanService(): PengaduanRequest{
        return RetrofitClient.getClient(API_BASE_PATH)!!.create(PengaduanRequest::class.java)
    }
}