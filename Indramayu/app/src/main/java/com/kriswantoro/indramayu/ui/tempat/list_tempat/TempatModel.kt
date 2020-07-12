package com.kriswantoro.indramayu.ui.tempat.list_tempat

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class TempatModel(
    //baka sing api mah ganti string kris
    val idTempat: String,
    val idKategoriTempat: String,
    val namaTempat: String,
    val fotoTempat: String,
    val lat: Double,
    val lng: Double,
    val idKategoriTempat2: String,
    val namaTempat2: String

) : Parcelable