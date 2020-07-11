package com.kriswantoro.indramayu.verifikasi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kriswantoro.indramayu.R
import com.kriswantoro.indramayu.intro.IntroActivity
import com.kriswantoro.indramayu.ui.beranda.buat_pengaduan.PengaduanActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.fragment_akun.view.*
import kotlinx.android.synthetic.main.fragment_beranda.*
import kotlinx.android.synthetic.main.fragment_beranda.view.*
import kotlinx.android.synthetic.main.fragment_beranda.view.btn_pengaduan

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        buttonSignUp.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
        btn_lanjutkan.setOnClickListener {
            startActivity(Intent(this, OTPActivity::class.java))
        }
    }
}
