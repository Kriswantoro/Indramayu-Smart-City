package com.kriswantoro.indramayu.ui.beranda.buat_pengaduan

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class KategoriPengaduanModel(
    @SerializedName("id_kategori_pengaduan") val idKategoriPengaduan: String = "",
    @SerializedName("id_dinas") val idDinas: Int = 0,
    @SerializedName("nama_kategori_pengaduan") val namaKategoriPengaduan: String = "",
    @SerializedName("nama_dinas") val namaDinas: String = ""
): Parcelable