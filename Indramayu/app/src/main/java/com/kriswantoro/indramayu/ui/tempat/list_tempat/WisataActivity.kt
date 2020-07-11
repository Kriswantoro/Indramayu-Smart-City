package com.kriswantoro.indramayu.ui.tempat.list_tempat

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.kriswantoro.indramayu.R
import com.kriswantoro.indramayu.ui.tempat.list_tempat.wisata.WisataAdapter
import com.kriswantoro.indramayu.ui.tempat.list_tempat.wisata.WisataModel
import org.json.JSONObject
import java.lang.Exception

class WisataActivity : AppCompatActivity() {

    private val mDataList = ArrayList<WisataModel>()
    private lateinit var adapter: WisataAdapter

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wisata)


        val list_wisata = this.findViewById(R.id.list_wisata) as RecyclerView

        list_wisata.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL,  false)


        val adapter =
            WisataAdapter(
                mDataList
            )

        AndroidNetworking.get("http://192.168.100.5:8000/tempatWisata/")
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject?) {
                    val jsonArray = response!!.optJSONArray("data")

                    try {
                        for (i in 0 until jsonArray?.length()!!){
                            val objectWisata = jsonArray?.optJSONObject(i)
                            mDataList.add(
                                WisataModel(
                                    objectWisata.getString("nama_tempat"),
                                    objectWisata.getDouble("lat"),
                                    objectWisata.getDouble("lng")
                                )
                            )
                        }
                    } catch (e: Exception){

                    }

                    list_wisata.adapter = adapter
                }

                override fun onError(anError: ANError?) {
                    Toast.makeText(this@WisataActivity, "Kesalahan Jaringan", Toast.LENGTH_LONG).show()
                }
            })

    }
}
