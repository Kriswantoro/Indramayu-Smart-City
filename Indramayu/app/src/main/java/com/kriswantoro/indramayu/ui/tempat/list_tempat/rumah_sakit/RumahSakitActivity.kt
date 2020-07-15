package com.kriswantoro.indramayu.ui.tempat.list_tempat.rumah_sakit

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.kriswantoro.indramayu.R
import com.kriswantoro.indramayu.ui.tempat.MapsActivity
import com.kriswantoro.indramayu.ui.tempat.list_tempat.TempatAdapter
import com.kriswantoro.indramayu.ui.tempat.list_tempat.TempatModel
import com.kriswantoro.indramayu.util.EndPoint
import com.kriswantoro.indramayu.util.VolleySingleton
import kotlinx.android.synthetic.main.activity_rumah_sakit.*
import kotlinx.android.synthetic.main.activity_wisata.*
import org.json.JSONException
import org.json.JSONObject

class RumahSakitActivity : AppCompatActivity() {

    private val mDataList = ArrayList<TempatModel>()
    private lateinit var adapter: TempatAdapter
    var idKategori: Int = 0

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rumah_sakit)

        list_r_sakit.layoutManager = LinearLayoutManager(this)
        idKategori = intent.getIntExtra("id_kategori", 0)
        getPolisi()

    }

    fun getPolisi() {
        val stringRequest = object : StringRequest(
            Method.POST, EndPoint.URL_GET_TEMPAT,
            Response.Listener<String> { response ->

                try {
                    val obj = JSONObject(response)

                    if (!obj.getBoolean("error")) {
//                        Toast.makeText(
//                            applicationContext,
//                            obj.getString("message"),
//                            Toast.LENGTH_SHORT
//                        ).show()

                        val array = obj.getJSONArray("data")
                        for (i in 0 until array.length()) {
                            val rumahsakitJson = array.getJSONObject(i)

                            val rumahSakit =
                                TempatModel(
                                    rumahsakitJson.getString("id_tempat"),
                                    rumahsakitJson.getString("id_kategori_tempat"),
                                    rumahsakitJson.getString("nama_tempat"),
                                    rumahsakitJson.getString("foto_tempat"),
                                    rumahsakitJson.getDouble("lat"),
                                    rumahsakitJson.getDouble("lng"),
                                    rumahsakitJson.getString("kategori_tempat.id_kategori_tempat"),
                                    rumahsakitJson.getString("kategori_tempat.nama_kategori")
                                )

                            mDataList.add(rumahSakit)
                            adapter =
                                TempatAdapter(
                                    mDataList
                                ) {
                                    //item adapter udah auto get position
                                    val intent = Intent(this, MapsActivity::class.java)
                                    intent.putExtra("nama_tempat", it.namaTempat)
                                    intent.putExtra("lat", it.lat)
                                    intent.putExtra("lng", it.lng)
                                    startActivity(intent)

                                    Toast.makeText(this, it.namaTempat, Toast.LENGTH_SHORT).show()
                                }
                        }
                        list_r_sakit.adapter = adapter
                    } else {
                        Toast.makeText(
                            applicationContext,
                            obj.getString("message"),
                            Toast.LENGTH_SHORT
                        ).show()
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
                params["id_kategori_tempat"] = idKategori.toString()

                return params
            }
        }
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest)
    }
}
