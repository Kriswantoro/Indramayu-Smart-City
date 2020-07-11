package com.kriswantoro.indramayu.ui.beranda

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.github.dhaval2404.imagepicker.sample.setDrawableImage
import com.kriswantoro.indramayu.ui.beranda.buat_pengaduan.PengaduanActivity
import com.kriswantoro.indramayu.R
import com.kriswantoro.indramayu.ui.beranda.buat_pengaduan.PengaduanAdapter
import com.kriswantoro.indramayu.ui.beranda.buat_pengaduan.PengaduanModel
import com.kriswantoro.indramayu.ui.beranda.panggilan_darurat.PanggilanDaruratActivity
import com.kriswantoro.indramayu.util.EndPoint
import kotlinx.android.synthetic.main.fragment_beranda.*
import kotlinx.android.synthetic.main.fragment_beranda.view.*
import kotlinx.android.synthetic.main.fragment_beranda.view.foto_profil
import org.json.JSONException
import org.json.JSONObject

class BerandaFragment : Fragment() {

    private val mDataList = ArrayList<PengaduanModel>()
    lateinit var listPengaduan: RecyclerView

    @SuppressLint("WrongConstant")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_beranda, container, false)
        root.btn_pengaduan.setOnClickListener {
            startActivity(Intent(context, PengaduanActivity::class.java))
        }
        root.btn_darurat.setOnClickListener {
            startActivity(Intent(context, PanggilanDaruratActivity::class.java))
        }

        getPengaduan()

        root.foto_profil.setDrawableImage(R.drawable.foto_profile, true)

        listPengaduan = root.findViewById(R.id.list_pengaduan)

        listPengaduan.layoutManager = LinearLayoutManager(context)

        return root
    }

    fun getPengaduan() {
        val stringRequest = StringRequest(
            Request.Method.GET,
            EndPoint.URL_GET_PENGADUAN,
            Response.Listener<String> { s ->
                try {
                    val obj = JSONObject(s)
                    if (!obj.getBoolean("error")) {
                        val array = obj.getJSONArray("data")

                        for (i in 0 until array.length()) {
                            val objectMenu = array.getJSONObject(i)
                            val menu = PengaduanModel(
                                objectMenu.getString("id_pengaduan"),
                                objectMenu.getString("judul_pengaduan"),
                                objectMenu.getString("kategori"),
                                objectMenu.getString("pesan"),
                                objectMenu.getString("foto_pengaduan"),
                                objectMenu.getString("lokasi_pengaduan")
                            )
                            mDataList.add(menu)
                            val adapter = PengaduanAdapter(mDataList)
                            listPengaduan.adapter = adapter
                        }
                    } else{
                        Toast.makeText(requireContext(), obj.getString("message"), Toast.LENGTH_LONG).show()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener {
                Toast.makeText(
                    requireContext(),
                    it.message,
                    Toast.LENGTH_LONG
                ).show()
            })
        val requestQueue = Volley.newRequestQueue(requireContext())
        requestQueue.add<String>(stringRequest)
    }
}
