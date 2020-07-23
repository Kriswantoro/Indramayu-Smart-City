package com.kriswantoro.indramayu.intro

import android.content.Context
import android.content.Intent
import com.kriswantoro.indramayu.MainActivity
import com.kriswantoro.indramayu.ui.akun.AkunModel

class SharedPref private constructor(mCtx: Context) {

    val isLoggedIn: Boolean
        get() {
            val sharedPreferences =
                ctx?.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            return sharedPreferences?.getString(KEY_ID, null) != null
        }

    val user: AkunModel
        get() {
            val sharedPreferences =
                ctx?.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            return AkunModel(
                sharedPreferences!!.getString(KEY_ID, null),
                sharedPreferences.getString(KEY_FOTO, null),
                sharedPreferences.getString(KEY_NAMA, null),
                sharedPreferences.getString(KEY_EMAIL, null),
                sharedPreferences.getString(KEY_NO_TELP, null)
            )
        }

    init {
        ctx = mCtx
    }

    fun userLogin(user: AkunModel){
        val sharedPreferences = ctx?.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        editor?.putString(KEY_ID, user.idPengguna)
        editor?.putString(KEY_FOTO, user.fotoPengguna)
        editor?.putString(KEY_NAMA, user.namaPengguna)
        editor?.putString(KEY_EMAIL, user.email)
        editor?.putString(KEY_NO_TELP, user.noTlpn)
        editor?.apply()
    }

    fun userLogout() {
        val sharedPreferences = ctx?.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        editor?.clear()
        editor?.apply()
        ctx?.startActivity(Intent(ctx, MainActivity::class.java))
    }

    companion object {
        private val SHARED_PREF_NAME = "my_shared_preff"
        private val KEY_ID = "keyidPengguna"
        private val KEY_FOTO = "keyfotoPengguna"
        private val KEY_NAMA = "keynamaPengguna"
        private val KEY_EMAIL = "keyemail"
        private val KEY_NO_TELP = "keynoTlpn"


        private var mInstance: SharedPref? = null
        private var ctx: Context? = null
        @Synchronized
        fun getInstance(mCtx: Context): SharedPref {
            if (mInstance == null) {
                mInstance = SharedPref(mCtx)
            }
            return mInstance as SharedPref
        }
    }
}