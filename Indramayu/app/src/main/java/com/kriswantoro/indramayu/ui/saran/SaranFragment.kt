package com.kriswantoro.indramayu.ui.saran

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.kriswantoro.indramayu.R
import com.kriswantoro.indramayu.ui.saran.buat_saran.TambahSaranActivity
import kotlinx.android.synthetic.main.fragment_saran.view.*
import org.json.JSONObject
import java.lang.Exception

class SaranFragment : Fragment() {

//    private val mDataList = ArrayList<SaranModel>()
//    private lateinit var adapter: SaranAdapter

    @SuppressLint("WrongConstant")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_saran, container, false)
        root.tmbh_saran.setOnClickListener {
            startActivity(Intent(context,
                TambahSaranActivity::class.java))
        }
        val list_saran = root.findViewById(R.id.list_saran) as RecyclerView

        list_saran.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL,  false)


//        AndroidNetworking.get("http://192.168.100.5:8000/")
//            .setPriority(Priority.MEDIUM)
//            .build()
//            .getAsJSONObject(object : JSONObjectRequestListener {
//                override fun onResponse(response: JSONObject?) {
//                    val jsonArray = response!!.optJSONArray("data")
//
//                    try {
//                        for (i in 0 until jsonArray?.length()!!){
//                            val jsonObject = jsonArray?.optJSONObject(i)
//                            mDataList.add(
//                                SaranModel(
//                                    jsonObject.getString("judul_saran"),
//                                    jsonObject.getString("deskripsi")
//                                )
//                            )
//                        }
//                    } catch (e: Exception){
//
//                    }
//
//                    list_saran.adapter = adapter
//                }
//
//                override fun onError(anError: ANError?) {
//                    Toast.makeText(context, "Kesalahan Jaringan", Toast.LENGTH_LONG).show()
//                }
//            })
//
        return root

    }
}
