package com.kriswantoro.indramayu.ui.beranda.buat_pengaduan

import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.android.parcel.Parcelize

@Parcelize
class PengaduanModel (
    var id_pengaduan : String,
    var idPengguna: String,
    var idDinas: String,
    var judulPengaduan : String,
    var kategori : String,
    var pesan : String,
    var fotoPengaduan : String,
    var lokasi : String,
    var status: String,
    var lat: String,
    var lng: String,
    var fotoPengguna: String,
    var namaPengguna: String

) : Parcelable