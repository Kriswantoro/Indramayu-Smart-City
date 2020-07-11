package com.kriswantoro.indramayu.ui.tempat.list_tempat.ibadah

import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.android.parcel.Parcelize

@Parcelize
class IbadahModel (
//    var foto_ibadah : String,
    var nama_ibadah : String,
    var jarak_ibadah : String
) : Parcelable