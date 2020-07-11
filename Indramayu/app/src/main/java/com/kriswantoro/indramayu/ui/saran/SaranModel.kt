package com.kriswantoro.indramayu.ui.saran

import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.android.parcel.Parcelize

@Parcelize
class SaranModel (
    var cv_judul_saran : String,
    var cv_deskripsi : String
//    var cv_tanggal_saran : String

) : Parcelable