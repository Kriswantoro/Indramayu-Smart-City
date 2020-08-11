package com.kriswantoro.indramayu.util.retrofit.model

import com.google.gson.annotations.SerializedName

data class KategoriDinasModel(
    @SerializedName("id_dinas") val idDinas: String = "",
    @SerializedName("nama_dinas") val namaDinas: String = ""
)