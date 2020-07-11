package com.kriswantoro.indramayu.ui.tempat.list_tempat.rumah_sakit

import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.android.parcel.Parcelize

@Parcelize
class RumahSakitModel (
//    var foto_r_sakit : String,
    var nama_r_sakit : String,
    var jarak_r_sakit : String
) : Parcelable