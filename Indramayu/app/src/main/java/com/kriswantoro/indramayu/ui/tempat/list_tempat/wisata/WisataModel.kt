package com.kriswantoro.indramayu.ui.tempat.list_tempat.wisata

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class WisataModel(
//    var foto_wisata : String,
    val nama_tempat: String,
    val lat: Double,
    val lng: Double

) : Parcelable