package com.kriswantoro.indramayu.util.retrofit.model

import com.google.gson.annotations.SerializedName

data class NomorPanggilanModel(
    @SerializedName("id_panggilan") val idPanggilan: Int,
    @SerializedName("nama") val namaPanggilan: String,
    @SerializedName("nomor") val nomorPanggilan: String
)