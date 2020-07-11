package com.kriswantoro.indramayu.ui.tempat.list_tempat.wisata

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class WisataModel(
    //baka sing api mah ganti string kris
    var foto_wisata : Int,
    val nama_tempat: String,
    val lat: Double,
    val lng: Double

) : Parcelable