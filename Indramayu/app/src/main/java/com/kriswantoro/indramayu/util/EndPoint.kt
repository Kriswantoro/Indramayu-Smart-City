package com.kriswantoro.indramayu.util

object EndPoint {

    const val BASE = "http://192.168.43.140/smartcityindramayu/"

    private val URL_ROOT = "http://192.168.43.140/smartcityindramayu/application/slim/v1/?apicall="

    //getpengaduan
    val URL_GET_PENGADUAN = URL_ROOT + "getpengaduan"

    //postpengaduan
    val URL_POST_PENGADUAN = URL_ROOT + "postpengaduan"

    //getkategori
    val URL_GET_KATEGORI = URL_ROOT + "getkategori"

    //gettempat
    val URL_GET_TEMPAT = URL_ROOT + "gettempat"

    //getsaran
    val URL_GET_SARAN = URL_ROOT + "getsaran"

    //postsaran
    val URL_POST_SARAN = URL_ROOT + "postsaran"
}