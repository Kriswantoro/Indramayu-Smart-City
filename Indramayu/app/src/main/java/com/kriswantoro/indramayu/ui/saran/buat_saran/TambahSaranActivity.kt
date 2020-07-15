package com.kriswantoro.indramayu.ui.saran.buat_saran

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.kriswantoro.indramayu.MainActivity
import com.kriswantoro.indramayu.R
import com.kriswantoro.indramayu.util.EndPoint
import com.kriswantoro.indramayu.util.VolleySingleton
import kotlinx.android.synthetic.main.activity_tambah_saran.*
import org.json.JSONException
import org.json.JSONObject
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.collections.HashMap

class TambahSaranActivity : AppCompatActivity() {

    private lateinit var edtJudulSaran: EditText
    private lateinit var edtDeskripsi: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_saran)

        edtJudulSaran = findViewById(R.id.judul_saran)
        edtDeskripsi = findViewById(R.id.deskripsi)

        btn_kirim_saran.setOnClickListener {
            when {
                edtJudulSaran.text.isEmpty() -> {
                    edtJudulSaran.error = "Judul saran tidak boleh kosong"
                    edtJudulSaran.requestFocus()
                }
                edtJudulSaran.text.isEmpty() -> {
                    edtJudulSaran.error = "Deskripsi tidak boleh kosong"
                    edtJudulSaran.requestFocus()
                }
                else -> {
                    addSaran()
                    startActivity(Intent(this, MainActivity::class.java))
                }
            }
        }
    }

    private fun addSaran() {

        val judulSaran = edtJudulSaran.text.toString()
        val desc = edtDeskripsi.text.toString()

        val stringRequest = object : StringRequest(
            Method.POST, EndPoint.URL_POST_SARAN,
            Response.Listener<String> { response ->
                try {
                    val obj = JSONObject(response)
                    Toast.makeText(applicationContext, obj.getString("message"), Toast.LENGTH_LONG)
                        .show()
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(
                    applicationContext,
                    error?.message,
                    Toast.LENGTH_LONG
                ).show()
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["judul_saran"] = judulSaran
                params["deskripsi"] = desc
                params["tgl_saran"] = ""
                return params
            }
        }

        //request
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest)
    }
}
