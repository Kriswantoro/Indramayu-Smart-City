package com.kriswantoro.indramayu.ui.saran.buat_saran

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import com.kriswantoro.indramayu.MainActivity
import com.kriswantoro.indramayu.R
import com.kriswantoro.indramayu.intro.SharedPref
import com.kriswantoro.indramayu.ui.beranda.buat_pengaduan.KategoriPengaduanModel
import com.kriswantoro.indramayu.util.EndPoint
import com.kriswantoro.indramayu.util.VolleySingleton
import com.kriswantoro.indramayu.util.retrofit.model.KategoriDinasModel
import com.kriswantoro.indramayu.util.retrofit.network.BaseService
import com.kriswantoro.indramayu.util.retrofit.network.response.BaseResponse
import com.kriswantoro.indramayu.verifikasi.LoginActivity
import kotlinx.android.synthetic.main.activity_pengaduan.*
import kotlinx.android.synthetic.main.activity_tambah_saran.*
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.collections.HashMap

class TambahSaranActivity : AppCompatActivity() {

    private lateinit var edtJudulSaran: EditText
    private lateinit var edtDeskripsi: EditText
    private lateinit var spinnerDinas: Spinner

    val mListKategoriDinas = ArrayList<KategoriDinasModel>()
    var idPengguna: String = ""
    var listSpinnerIdDinas: String = ""
    var listDataSpinner: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_saran)

        edtJudulSaran = findViewById(R.id.judul_saran)
        edtDeskripsi = findViewById(R.id.deskripsi)
        spinnerDinas = findViewById(R.id.spin_kategori_instansi)

        getKategoriDinas()

        btn_kirim_saran.setOnClickListener {

            if (SharedPref.getInstance(this).isLoggedIn) {
                val user = SharedPref.getInstance(this).user
                idPengguna = user.idPengguna.toString()
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
                    }
                }
            } else {
                Toast.makeText(this, "You're not Login", Toast.LENGTH_LONG).show()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
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
                    startActivity(Intent(this, MainActivity::class.java))
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
                params["id_pengguna"] = idPengguna
                params["id_dinas"] = listSpinnerIdDinas
                params["judul_saran"] = judulSaran
                params["deskripsi"] = desc
                params["tgl_saran"] = ""
                params["name"] = ""
                params["judul"] = ""
                params["deskripsi"] = ""
                params["is_read"] = ""
                return params
            }
        }

        //request
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest)
    }

    fun getKategoriDinas() {
        BaseService.getKategoriDinasService().getKategoriDinas("getkategoridinas")
            .enqueue(object : Callback<BaseResponse<KategoriDinasModel>> {
                override fun onFailure(
                    call: Call<BaseResponse<KategoriDinasModel>>,
                    t: Throwable
                ) {
                    Toast.makeText(this@TambahSaranActivity, t.message, Toast.LENGTH_LONG).show()
                }

                override fun onResponse(
                    call: Call<BaseResponse<KategoriDinasModel>>,
                    response: retrofit2.Response<BaseResponse<KategoriDinasModel>>
                ) {
                    val responseData = response.body()?.response
                    if (response.body()?.code == 200) {
                        val saveData = Gson().toJson(responseData)

                        mListKategoriDinas.addAll(responseData!!.toList())

                        spinnerKategori(mListKategoriDinas)

                        Log.e("listKategori", saveData.toString())
                    }
                }
            })
    }

    fun spinnerKategori(data: ArrayList<KategoriDinasModel>) {
        /* kategori Type Spinner */
        val sizeKategori = data.size
        val spinnerKategoriItems = arrayOfNulls<String>(sizeKategori)
        val spinnerKategoriPost = arrayOfNulls<String>(sizeKategori)

        if (sizeKategori > 0) {
            for (i in 0 until data.size) {
                spinnerKategoriItems[i] = data[i].namaDinas
                spinnerKategoriPost[i] = data[i].idDinas
            }
        }

        val spinnerKategoriAdapter = ArrayAdapter(
            this@TambahSaranActivity,
            android.R.layout.simple_spinner_dropdown_item,
            spinnerKategoriItems
        )

        spinnerDinas.adapter = spinnerKategoriAdapter
        spinnerDinas.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val position = spinnerDinas.selectedItemPosition
                listDataSpinner = spinnerKategoriItems[position].toString()
                listSpinnerIdDinas = spinnerKategoriPost[position].toString()
            }

        }
    }
}
