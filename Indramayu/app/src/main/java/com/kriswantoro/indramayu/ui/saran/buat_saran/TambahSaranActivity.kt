package com.kriswantoro.indramayu.ui.saran.buat_saran

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONArrayRequestListener
import com.kriswantoro.indramayu.MainActivity
import com.kriswantoro.indramayu.R
import com.kriswantoro.indramayu.ui.saran.SaranFragment
import kotlinx.android.synthetic.main.activity_tambah_saran.*
import org.json.JSONArray

class TambahSaranActivity : AppCompatActivity() {

    private lateinit var judul_saran: EditText
    private lateinit var deskripsi: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_saran)

        judul_saran = findViewById(R.id.judul_saran)
        deskripsi = findViewById(R.id.deskripsi)

        btn_kirim_saran.setOnClickListener {

            AndroidNetworking.post("http://192.168.100.5:8000/saran/")
                .addBodyParameter("judul_saran", judul_saran.text.toString())
                .addBodyParameter("deskripsi", deskripsi.text.toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(object : JSONArrayRequestListener {
                    override fun onResponse(response: JSONArray?) {

                        Toast.makeText(this@TambahSaranActivity,"Data Telah Disimpan", Toast.LENGTH_LONG).show()
                    }

                    override fun onError(anError: ANError?) {
                        Toast.makeText(this@TambahSaranActivity,"Tidak Ada Jaringan", Toast.LENGTH_LONG).show()
                    }
            })

            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}
