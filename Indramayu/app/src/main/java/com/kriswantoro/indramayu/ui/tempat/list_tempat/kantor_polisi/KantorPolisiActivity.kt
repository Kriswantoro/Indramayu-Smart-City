package com.kriswantoro.indramayu.ui.tempat.list_tempat.kantor_polisi

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
import kotlinx.android.synthetic.main.activity_kantor_polisi.*
import kotlinx.android.synthetic.main.activity_wisata.*
import org.json.JSONException
import org.json.JSONObject

class KantorPolisiActivity : AppCompatActivity() {

    private val mDataList = ArrayList<TempatModel>()
    private lateinit var adapter: TempatAdapter
    var idKategori: Int = 0

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kantor_polisi)

        list_k_polisi.layoutManager = LinearLayoutManager(this)
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
                            val polisiJson = array.getJSONObject(i)

                            val polisi =
                                TempatModel(
                                    polisiJson.getString("id_tempat"),
                                    polisiJson.getString("id_kategori_tempat"),
                                    polisiJson.getString("nama_tempat"),
                                    polisiJson.getString("foto_tempat"),
                                    polisiJson.getDouble("lat"),
                                    polisiJson.getDouble("lng")
                                )

                            mDataList.add(polisi)
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
                        list_k_polisi.adapter = adapter
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
