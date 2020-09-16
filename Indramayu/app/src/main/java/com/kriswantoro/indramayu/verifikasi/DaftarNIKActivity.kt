package com.kriswantoro.indramayu.verifikasi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.toolbox.StringRequest
import com.kriswantoro.indramayu.R
import com.kriswantoro.indramayu.util.EndPoint
import com.kriswantoro.indramayu.util.VolleySingleton
import kotlinx.android.synthetic.main.activity_daftar_n_i_k.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.buttonMasuk
import org.json.JSONException
import org.json.JSONObject

class DaftarNIKActivity : AppCompatActivity() {

    private lateinit var edt_dNik: EditText
    private lateinit var edt_dNama: EditText
    private lateinit var edt_dEmail: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daftar_n_i_k)

        edt_dNik = findViewById(R.id.d_nik)
        edt_dNama = findViewById(R.id.d_nama)
        edt_dEmail = findViewById(R.id.d_email)

        btn_daftar_nik.setOnClickListener{daftarnik()}
        buttonMasuk.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
    private fun daftarnik() {
        val nik = edt_dNik.text.toString()
        val nama = edt_dNama.text.toString()
        val email = edt_dEmail.text.toString()

        val stringRequest = object : StringRequest(
            Method.POST, EndPoint.URL_DAFTARNIK,
            com.android.volley.Response.Listener<String> { response ->
                try {
                    val obj = JSONObject(response)
                    when {
                        obj.getInt("code") == 200 -> {
                            Toast.makeText(
                                applicationContext,
                                "NIK Sukses Terdaftar ",
                                Toast.LENGTH_LONG
                            )
                                .show()
                            startActivity(Intent(this, LoginActivity::class.java))
                        }
                        obj.getInt("code") == 400 -> {
                            Toast.makeText(
                                applicationContext,
                                "No Telfon atau NIK anda sudah terdaftar",
                                Toast.LENGTH_LONG
                            )
                                .show()
                        }
                        else -> Toast.makeText(
                            applicationContext,
                            obj.getString("message"),
                            Toast.LENGTH_LONG
                        )
                            .show()
                    }

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            com.android.volley.Response.ErrorListener { error ->
                Toast.makeText(
                    applicationContext,
                    error?.message,
                    Toast.LENGTH_LONG
                ).show()
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["nik"] = nik
                params["nama"] = nama
                params["email"] = email
                return params
            }
        }

        //request
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest)
    }
}