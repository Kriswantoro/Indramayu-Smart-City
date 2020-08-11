package com.kriswantoro.indramayu.ui.saran

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.kriswantoro.indramayu.R
import com.kriswantoro.indramayu.intro.SharedPref
import com.kriswantoro.indramayu.ui.beranda.buat_pengaduan.PengaduanActivity
import com.kriswantoro.indramayu.ui.saran.buat_saran.TambahSaranActivity
import com.kriswantoro.indramayu.util.EndPoint
import com.kriswantoro.indramayu.verifikasi.LoginActivity
import kotlinx.android.synthetic.main.fragment_saran.*
import kotlinx.android.synthetic.main.fragment_saran.view.*
import org.json.JSONException
import org.json.JSONObject

class SaranFragment : Fragment() {

    private val mDataList = ArrayList<SaranModel>()
    private lateinit var adapter: SaranAdapter
    lateinit var listSaran: RecyclerView
    var idPengguna: String = ""

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
        if (SharedPref.getInstance(requireContext()).isLoggedIn) {

            val user = SharedPref.getInstance(requireContext()).user
            idPengguna = user.idPengguna.toString()

            listSaran()
        } else {
            Toast.makeText(requireContext(), "You're not Loggedin", Toast.LENGTH_LONG).show()
            startActivity(Intent(context, LoginActivity::class.java))
        }
        listSaran = root.findViewById(R.id.list_saran)
        listSaran.layoutManager = LinearLayoutManager(context)

        return root
    }

    private fun listSaran(){
            val stringRequest = object : StringRequest(
                Method.POST, EndPoint.URL_GET_SARAN,
                Response.Listener<String> { response ->
                    try {
                        val obj = JSONObject(response)
                        if (!obj.getBoolean("error")) {
                            val array = obj.getJSONArray("response")

                            for (i in 0 until array.length()) {
                                val objectSaran = array.getJSONObject(i)
                                val saran = SaranModel(
                                    objectSaran.getString("id_saran"),
                                    objectSaran.getString("id_pengguna"),
                                    objectSaran.getString("id_dinas"),
                                    objectSaran.getString("nama_pengguna"),
                                    objectSaran.getString("nama_dinas"),
                                    objectSaran.getString("judul_saran"),
                                    objectSaran.getString("deskripsi"),
                                    objectSaran.getString("tgl_saran")
                                )
                                mDataList.add(saran)
                                adapter = SaranAdapter(mDataList)
                                list_saran.adapter = adapter
                            }
                        } else{
                            Toast.makeText(requireContext(), obj.getString("message"), Toast.LENGTH_LONG).show()
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
                }) {
                @Throws(AuthFailureError::class)
                override fun getParams(): MutableMap<String, String> {
                    val params = HashMap<String, String>()
                    params["id_pengguna"] = idPengguna

                    return params
                }
            }
            val requestQueue = Volley.newRequestQueue(requireContext())
            requestQueue.add<String>(stringRequest)
    }
}
