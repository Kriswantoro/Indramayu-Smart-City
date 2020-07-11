package com.kriswantoro.indramayu.ui.beranda.buat_pengaduan

import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.android.parcel.Parcelize

@Parcelize
class PengaduanModel (
//    var list_pengaduan : String,
//    var lokasi_tempat : String,
//    var id_diproses : String,
// //   var foto_pengaduan: String,
////    var foto_profil : String,
//    var id_deskripsi : String,
//    var id_deskripsi : String,
//    var id_deskripsi : String,
    var id_pengaduan : String,
    var judulPengaduan : String,
    var kategori : String,
    var pesan : String,
    var foto_pengaduan : String,
    var lokasi_pengaduan : String
) : Parcelable