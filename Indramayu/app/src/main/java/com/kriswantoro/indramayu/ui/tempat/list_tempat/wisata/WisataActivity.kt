package com.kriswantoro.indramayu.ui.tempat.list_tempat.wisata

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
import kotlinx.android.synthetic.main.activity_wisata.*
import org.json.JSONException
import org.json.JSONObject

class WisataActivity : AppCompatActivity() {

    private val mDataList = ArrayList<TempatModel>()
    private lateinit var adapter: TempatAdapter
    var idKategori: Int = 0

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wisata)

        list_wisata.layoutManager = LinearLayoutManager(this)
        idKategori = intent.getIntExtra("id_kategori", 0)
        getWisata()
    }

    fun getWisata() {

        val stringRequest = object : StringRequest(
            Method.POST, EndPoint.URL_GET_TEMPAT,
            Response.Listener<String> { response ->

                try {
                    val obj = JSONObject(response)

                    if (!obj.getBoolean("error")) {
                        Toast.makeText(
                            applicationContext,
                            obj.getString("message"),
                            Toast.LENGTH_SHORT
                        ).show()

                        val array = obj.getJSONArray("data")
                        for (i in 0 until array.length()) {
                            val wisataJson = array.getJSONObject(i)

                            val wisata =
                                TempatModel(
                                    wisataJson.getString("id_tempat"),
                                    wisataJson.getString("id_kategori_tempat"),
                                    wisataJson.getString("nama_tempat"),
                                    wisataJson.getString("foto_tempat"),
                                    wisataJson.getDouble("lat"),
                                    wisataJson.getDouble("lng"),
                                    wisataJson.getString("kategori_tempat.id_kategori_tempat"),
                                    wisataJson.getString("kategori_tempat.nama_tempat")
                                )

                            mDataList.add(wisata)
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
                        list_wisata.adapter = adapter
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

//        mDataList2.add(WisataModel(R.drawable.gambar,"Pantai Glayem", -6.414827, 108.433787))
//        mDataList2.add(WisataModel(R.drawable.friends,"Pantai Tirtamaya", -6.407628, 108.425905))
//        mDataList2.add(WisataModel(R.drawable.i,"Pantai karangsong",-6.305388, 108.368601))
//        mDataList2.add(WisataModel(R.drawable.ibadah,"Pantai Panjiwa", -6.328259, 108.112269))
//        mDataList2.add(WisataModel(R.drawable.w,"Polindra",-6.408163, 108.281704 ))
}
