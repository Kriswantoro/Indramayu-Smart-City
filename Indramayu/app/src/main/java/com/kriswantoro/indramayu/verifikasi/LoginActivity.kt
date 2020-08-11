package com.kriswantoro.indramayu.verifikasi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.kriswantoro.indramayu.MainActivity
import com.kriswantoro.indramayu.R
import com.kriswantoro.indramayu.intro.IntroActivity
import com.kriswantoro.indramayu.intro.SharedPref
import com.kriswantoro.indramayu.ui.akun.AkunModel
import com.kriswantoro.indramayu.ui.beranda.buat_pengaduan.PengaduanActivity
import com.kriswantoro.indramayu.util.EndPoint
import com.kriswantoro.indramayu.util.VolleySingleton
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_pengaduan.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.fragment_akun.*
import kotlinx.android.synthetic.main.fragment_akun.view.*
import kotlinx.android.synthetic.main.fragment_beranda.*
import kotlinx.android.synthetic.main.fragment_beranda.view.*
import kotlinx.android.synthetic.main.fragment_beranda.view.btn_pengaduan
import org.json.JSONException
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        buttonSignUp.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
        btn_lanjutkan.setOnClickListener {
            login()
        }

//        val edtNoTelp: EditText = findViewById(R.id.edtNoTelp)
//        edtNoTelp.setOnEditorActionListener { textView, i, keyEvent ->
//            if (i==EditorInfo.IME_ACTION_SEARCH){
//                login()
//            }else{
//                Toast.makeText(this, "Data ora weruh", Toast.LENGTH_LONG).show()
//            }
//            true
//        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    fun login() {
        val noTelp = edtNoTelp.text.toString()

        if (TextUtils.isEmpty(noTelp)) {
            edtNoTelp.error = "Please enter No Tlpn"
            edtNoTelp.requestFocus()
            return
        }

        val stringRequest = object : StringRequest(
            Method.POST, EndPoint.URL_LOGIN,
            Response.Listener<String> { response ->

                try {
                    val obj = JSONObject(response)

                    if (!obj.getBoolean("error")) {
                        Toast.makeText(
                            applicationContext,
                            "Welcome to Indramayu Smart City",
                            Toast.LENGTH_SHORT
                        ).show()

                        val array = obj.getJSONArray("data")
                        for (i in 0 until array.length()) {
                            val userJson = array.getJSONObject(i)

                            val user = AkunModel(
                                userJson.getString("id_pengguna"),
                                userJson.getString("foto_pengguna"),
                                userJson.getString("nama_pengguna"),
                                userJson.getString("email"),
                                userJson.getString("no_tlpn")
                            )

                            SharedPref.getInstance(applicationContext).userLogin(user)
                            finish()
                            startActivity(Intent(applicationContext, MainActivity::class.java))
//                            Toast.makeText(this, "Welcome to ISC", Toast.LENGTH_LONG).show()
                        }
                    } else {

                        //Toast.makeText(applicationContext, "Kode Pendonor tidak ditemukan, harap masukkan dengan benar", Toast.LENGTH_SHORT).show()
                        AlertDialog.Builder(this)
                            .setTitle("Peringatan !")
                            .setMessage("Maaf, Nomer Telepon tidak ditemukan, harap masukkan dengan benar")
                            .setPositiveButton("Ok", { dialog, which -> })
                            .setCancelable(true)
                            .show()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(applicationContext, error.message, Toast.LENGTH_SHORT).show()
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["no_tlpn"] = noTelp

                return params
            }
        }
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest)
    }
}
