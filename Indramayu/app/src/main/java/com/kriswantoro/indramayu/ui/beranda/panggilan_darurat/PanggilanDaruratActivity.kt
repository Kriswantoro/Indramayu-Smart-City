package com.kriswantoro.indramayu.ui.beranda.panggilan_darurat

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kriswantoro.indramayu.R
import kotlinx.android.synthetic.main.activity_panggilan_darurat.*

class PanggilanDaruratActivity : AppCompatActivity() {

    internal  var number1:String?="+6289607424789"
    internal  var number2:String?="+6289607424789"
    internal  var number3:String?="+6289607424789"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_panggilan_darurat)

            btn_ktr_polisi.setOnClickListener {
                //Dialer intent
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + Uri.encode(number1)))
                startActivity(intent)
            }

            btn_rumah_sakit.setOnClickListener {
                //Dialer intent
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + Uri.encode(number2)))
                startActivity(intent)
            }

            btn_p_kebakaran.setOnClickListener {
                //Dialer intent
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + Uri.encode(number3)))
                startActivity(intent)
            }
    }
}
