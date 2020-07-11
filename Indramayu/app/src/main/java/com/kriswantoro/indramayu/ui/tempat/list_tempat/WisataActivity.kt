package com.kriswantoro.indramayu.ui.tempat.list_tempat

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.kriswantoro.indramayu.R
import com.kriswantoro.indramayu.ui.tempat.MapsActivity
import com.kriswantoro.indramayu.ui.tempat.list_tempat.wisata.WisataAdapter
import com.kriswantoro.indramayu.ui.tempat.list_tempat.wisata.WisataModel
import kotlinx.android.synthetic.main.activity_wisata.*
import org.json.JSONObject
import java.lang.Exception

class WisataActivity : AppCompatActivity() {

    private val mDataList = ArrayList<WisataModel>()
    var mDataList2 = ArrayList<WisataModel>()
    private lateinit var adapter: WisataAdapter

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wisata)

        initList()

        list_wisata.layoutManager = LinearLayoutManager(this)

        adapter = WisataAdapter(mDataList2) {
            //item adapter udah auto get position
            val intent = Intent(this, MapsActivity::class.java)
            intent.putExtra("nama", it.nama_tempat)
            intent.putExtra("lat", it.lat)
            intent.putExtra("lng", it.lng)
            startActivity(intent)

            Toast.makeText(this, it.nama_tempat, Toast.LENGTH_SHORT).show()
        }
        list_wisata.adapter = adapter

//        AndroidNetworking.get("http://192.168.100.5:8000/tempatWisata/")
//            .setPriority(Priority.MEDIUM)
//            .build()
//            .getAsJSONObject(object : JSONObjectRequestListener {
//                override fun onResponse(response: JSONObject?) {
//                    val jsonArray = response!!.optJSONArray("data")
//
//                    try {
//                        for (i in 0 until jsonArray?.length()!!) {
//                            val objectWisata = jsonArray?.optJSONObject(i)
//                            mDataList.add(
//                                WisataModel(
//                                    objectWisata.getString("nama_tempat"),
//                                    objectWisata.getDouble("lat"),
//                                    objectWisata.getDouble("lng")
//                                )
//                            )
//                        }
//                    } catch (e: Exception) {
//
//                    }
//
////                    list_wisata.adapter = adapter
//                }
//
//                override fun onError(anError: ANError?) {
//                    Toast.makeText(this@WisataActivity, "Kesalahan Jaringan", Toast.LENGTH_LONG)
//                        .show()
//                }
//            })
    }

    fun initList() {
        mDataList2.add(WisataModel(R.drawable.gambar,"Pantai Glayem", -6.414827, 108.433787))
        mDataList2.add(WisataModel(R.drawable.friends,"Pantai Tirtamaya", -6.407628, 108.425905))
        mDataList2.add(WisataModel(R.drawable.i,"Pantai karangsong",-6.305388, 108.368601))
        mDataList2.add(WisataModel(R.drawable.ibadah,"Pantai Panjiwa", -6.328259, 108.112269))
        mDataList2.add(WisataModel(R.drawable.w,"Polindra",-6.408163, 108.281704 ))
    }
}
