package com.kriswantoro.indramayu.ui.beranda

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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
import com.kriswantoro.indramayu.intro.SharedPref
import com.kriswantoro.indramayu.ui.beranda.buat_pengaduan.PengaduanAdapter
import com.kriswantoro.indramayu.ui.beranda.buat_pengaduan.PengaduanModel
import com.kriswantoro.indramayu.ui.beranda.panggilan_darurat.PanggilanDaruratActivity
import com.kriswantoro.indramayu.util.EndPoint
import com.kriswantoro.indramayu.verifikasi.LoginActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_beranda.view.*
import kotlinx.android.synthetic.main.fragment_beranda.view.foto_profil
import org.json.JSONException
import org.json.JSONObject

class BerandaFragment : Fragment() {

    private val mDataList = ArrayList<PengaduanModel>()
    lateinit var listPengaduan: RecyclerView

    var ctx: Context? = null
    private var idPengguna = ""
    private lateinit var namaPengguna: TextView
    private lateinit var fotoPengguna: com.mikhaellopez.circularimageview.CircularImageView

    @SuppressLint("WrongConstant")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_beranda, container, false)
        root.btn_pengaduan.setOnClickListener {
            if (SharedPref.getInstance(requireContext()).isLoggedIn) {
                startActivity(Intent(context, PengaduanActivity::class.java))
            } else {
                Toast.makeText(requireContext(), "You're not Loggedin", Toast.LENGTH_LONG).show()
                startActivity(Intent(context, LoginActivity::class.java))
            }

        }
        root.btn_darurat.setOnClickListener {
            if (SharedPref.getInstance(requireContext()).isLoggedIn) {
                startActivity(Intent(context, PanggilanDaruratActivity::class.java))
            } else {
                Toast.makeText(requireContext(), "You're not Loggedin", Toast.LENGTH_LONG).show()
                startActivity(Intent(context, LoginActivity::class.java))
            }
        }
        namaPengguna = root.findViewById(R.id.nama_pengguna)
        fotoPengguna = root.findViewById(R.id.foto_profil)

        check()
        getPengaduan()

        listPengaduan = root.findViewById(R.id.list_pengaduan)

        listPengaduan.layoutManager = LinearLayoutManager(context)

        return root
    }

    fun check() {
        if (SharedPref.getInstance(requireContext()).isLoggedIn) {
            val user = SharedPref.getInstance(requireContext()).user
            namaPengguna.text = user.namaPengguna
            if (user.fotoPengguna == "") {
                Picasso.get().load(R.drawable.foto_profile).into(fotoPengguna)
            } else Picasso.get().load(user.fotoPengguna).into(fotoPengguna)
        } else {
            startActivity(Intent(context, LoginActivity::class.java))
        }
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
                                objectMenu.getString("id_pengguna"),
                                objectMenu.getString("id_dinas"),
                                objectMenu.getString("judul_pengaduan"),
                                objectMenu.getString("kategori"),
                                objectMenu.getString("pesan"),
                                objectMenu.getString("foto_pengaduan"),
                                objectMenu.getString("lokasi"),
                                objectMenu.getString("status"),
                                objectMenu.getString("foto_pengguna"),
                                objectMenu.getString("nama_pengguna")
                            )
                            mDataList.add(menu)
                            val adapter = PengaduanAdapter(mDataList)
                            listPengaduan.adapter = adapter
                        }
                    } else {
                        Toast.makeText(
                            requireContext(),
                            obj.getString("message"),
                            Toast.LENGTH_LONG
                        ).show()
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
