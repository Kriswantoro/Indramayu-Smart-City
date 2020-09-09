package com.kriswantoro.indramayu.ui.tempat.list_tempat

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class TempatModel(
    //baka sing api mah ganti string kris
    val id_tempat: String,
    val id_kategori_tempat: String,
    val namaTempat: String,
    val fotoTempat: String,
    val lat: Double,
    val lng: Double

) : Parcelable