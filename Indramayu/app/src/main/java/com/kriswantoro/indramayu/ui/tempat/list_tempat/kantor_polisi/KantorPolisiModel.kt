package com.kriswantoro.indramayu.ui.tempat.list_tempat.kantor_polisi

import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.android.parcel.Parcelize

@Parcelize
class KantorPolisiModel (
//    var foto_r_sakit : String,
    var nama_k_polisi : String,
    var jarak_k_polisi : String
) : Parcelable