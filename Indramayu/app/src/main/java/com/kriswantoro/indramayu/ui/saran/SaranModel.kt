package com.kriswantoro.indramayu.ui.saran

import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.android.parcel.Parcelize

@Parcelize
class SaranModel (
    val idSaran: String,
    val judulSaran: String,
    val descSaran: String,
    val tglSaran: String

) : Parcelable