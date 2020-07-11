package com.kriswantoro.indramayu.util

object EndPoint {

    private val URL_ROOT = "http://192.168.43.140/smartcityindramayu/application/slim/v1/?apicall="

    //getpengaduan
    val URL_GET_PENGADUAN = URL_ROOT + "getpengaduan"

    //postpengaduan
    val URL_POST_PENGADUAN = URL_ROOT + "postpengaduan"
}