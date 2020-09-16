package com.kriswantoro.indramayu.ui.akun

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.kriswantoro.indramayu.R
import com.kriswantoro.indramayu.intro.SharedPref
import com.kriswantoro.indramayu.ui.beranda.buat_pengaduan.PengaduanAdapter
import com.kriswantoro.indramayu.ui.beranda.buat_pengaduan.PengaduanModel
import com.kriswantoro.indramayu.ui.tempat.MapsActivity
import com.kriswantoro.indramayu.util.EndPoint
import com.kriswantoro.indramayu.util.VolleySingleton
import org.json.JSONException
import org.json.JSONObject

class RiwayatPengaduan : AppCompatActivity() {

    private val mDataList = ArrayList<PengaduanModel>()
    lateinit var listPengaduan: RecyclerView
    lateinit var adapter: PengaduanAdapter

    var idPengguna: String = ""
    var status: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_riwayat_pengaduan)

        status = intent.getStringExtra("status")!!

        val user = SharedPref.getInstance(this).user

        idPengguna = user.idPengguna.toString()

        listPengaduan = findViewById(R.id.list_riwayat)
        listPengaduan.layoutManager = LinearLayoutManager(this)
        getRiwayat(status, idPengguna)
    }

    fun getRiwayat(status: String, idPengguna: String) {
        val stringRequest = object : StringRequest(
            Method.POST, EndPoint.URL_GET_RPENGADUAN,
            Response.Listener<String> { response ->

                try {
                    val obj = JSONObject(response)

                    if (!obj.getBoolean("error")) {

                        val array = obj.getJSONArray("data")
                        for (i in 0 until array.length()) {
                            val objectMenu = array.getJSONObject(i)
                            val menu = PengaduanModel(
                                objectMenu.getString("id_pengaduan"),
                                objectMenu.getString("id_pengguna"),
                                objectMenu.getString("id_dinas"),
                                objectMenu.getString("judul_pengaduan"),
                                objectMenu.getString("kategori"),
                                objectMenu.getString("pesan"),
                                objectMenu.getString("foto_pengaduan"),
                                objectMenu.getString("lokasi"),
                                objectMenu.getString("status"),
                                objectMenu.getString("lat"),
                                objectMenu.getString("lng"),
                                objectMenu.getString("tgl_pengaduan"),
                                objectMenu.getString("foto_pengguna"),
                                objectMenu.getString("nama_pengguna")
                            )
                            mDataList.add(menu)
                            adapter = PengaduanAdapter(mDataList) {
                                val intent = Intent(this, MapsActivity::class.java)
                                intent.putExtra("nama_tempat", it.lokasi)
                                intent.putExtra("lat", it.lat.toDouble())
                                intent.putExtra("lng", it.lng.toDouble())
                                startActivity(intent)
                            }
                            listPengaduan.adapter = adapter
                        }
                    } else {
                        Toast.makeText(this, "Data Kosong", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["status"] = status
                params["id_pengguna"] = idPengguna

                return params
            }
        }
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest)
    }
}