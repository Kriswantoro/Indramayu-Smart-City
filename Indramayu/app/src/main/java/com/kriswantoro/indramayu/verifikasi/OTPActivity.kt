package com.kriswantoro.indramayu.verifikasi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kriswantoro.indramayu.MainActivity
import com.kriswantoro.indramayu.R
import kotlinx.android.synthetic.main.activity_o_t_p.*

class OTPActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_o_t_p)

        buttonSignIn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}
