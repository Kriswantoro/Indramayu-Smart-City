package com.kriswantoro.indramayu.util

object EndPoint {

    private const val URL_ROOT = "${Constant.API_BASE_PATH}slim/v1/?apicall="
//    private val URL_ROOT = "http://10.0.2.2/smartcityindramayu/slim/v1/?apicall="


    //getpengaduan
    val URL_GET_PENGADUAN = URL_ROOT + "getpengaduan"

    //getrpengaduan
    val URL_GET_RPENGADUAN = URL_ROOT + "getrpengaduan"

    //postpengaduan
    val URL_POST_PENGADUAN = URL_ROOT + "postpengaduan"

    //getkategori
    val URL_GET_KATEGORI = URL_ROOT + "getkategori"

    //getkategoripengaduan
    val URL_GET_KATEGORI_PENGADUAN = URL_ROOT + "getkategoripengaduan"

    //gettempat
    val URL_GET_TEMPAT = URL_ROOT + "gettempat"

    //getsaran
    val URL_GET_SARAN = URL_ROOT + "getsaran"

    //postsaran
    val URL_POST_SARAN = URL_ROOT + "postsaran"

    //login
    val URL_LOGIN = URL_ROOT + "getlogin"

    //ceknik
    val URL_CHECK_NIK = URL_ROOT + "checkNik"

    //register
    val URL_REGISTER = URL_ROOT + "postregister"

    //daftarnik
    val URL_DAFTARNIK = URL_ROOT + "postdaftarnik"

    //riwayat
    val URL_RIWAYAT = URL_ROOT + "getriwayat"

    //updateuser
    val URL_UPDATE_USER = URL_ROOT + "updateuser"

    //riwayat
    val URL_RIWAYAT_PENGADUAN = URL_ROOT + "getriwayatpengaduan"
}
