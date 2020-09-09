package com.kriswantoro.indramayu.ui.akun

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest

import com.kriswantoro.indramayu.R
import com.kriswantoro.indramayu.intro.SharedPref
import com.kriswantoro.indramayu.ui.akun.edit_akun.EditAkunAktivity
import com.kriswantoro.indramayu.ui.tempat.list_tempat.ibadah.IbadahActivity
import com.kriswantoro.indramayu.util.EndPoint
import com.kriswantoro.indramayu.util.VolleySingleton
import com.kriswantoro.indramayu.verifikasi.LoginActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_akun.*
import kotlinx.android.synthetic.main.fragment_akun.view.*
import org.json.JSONException
import org.json.JSONObject

class AkunFragment : Fragment() {

    private lateinit var namaPengguna: TextView
    private lateinit var nomorPengguna: TextView
    private lateinit var fotoPengguna: com.mikhaellopez.circularimageview.CircularImageView

    lateinit var belum: TextView
    lateinit var sedang: TextView
    lateinit var selesai: TextView
    lateinit var tolak: TextView

    var idPengguna: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_akun, container, false)

        belum = root.findViewById(R.id.riwayat_belum)
        sedang = root.findViewById(R.id.riwayat_sedang)
        selesai = root.findViewById(R.id.riwayat_selesai)
        tolak = root.findViewById(R.id.riwayat_tolak)

        if (SharedPref.getInstance(requireContext()).isLoggedIn) {

            namaPengguna = root.findViewById(R.id.nama_pengguna)
            nomorPengguna = root.findViewById(R.id.nomor_pengguna)
            fotoPengguna = root.findViewById(R.id.foto_profil)

            val user = SharedPref.getInstance(requireContext()).user

            val base64String = user.fotoPengguna
            val pureBase64Encoded = base64String?.substring(base64String.indexOf(",") + 1)
            val imageBytes = Base64.decode(pureBase64Encoded, Base64.DEFAULT)
            val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

            idPengguna = user.idPengguna.toString()
            namaPengguna.text = user.namaPengguna
            nomorPengguna.text = user.noTlpn
            if (user.fotoPengguna == "") {
                Picasso.get().load(R.drawable.foto_profile).into(fotoPengguna)
            } else fotoPengguna.setImageBitmap(decodedImage)
        } else {
            Toast.makeText(requireContext(), "You're not Loggedin", Toast.LENGTH_LONG).show()
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }

        getRiwayatBelum("1", idPengguna)
        getRiwayatSedang("2", idPengguna)
        getRiwayatSelesai("3", idPengguna)
        getRiwayatTolak("4", idPengguna)

        root.btn_keluar.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Alert!")
                .setMessage("Apakah anda yakin ingin logout ?")
                .setPositiveButton("Ya") { _, _ ->
                    SharedPref.getInstance(requireContext()).userLogout()
                    activity?.finish()
                    startActivity(Intent(context, LoginActivity::class.java))
                    onDestroy()
                }
                .setNegativeButton("Tidak") { _, _ -> }
                .show()
        }
        root.edit_profil.setOnClickListener {
//            Toast.makeText(requireContext(), "Soon!!!", Toast.LENGTH_SHORT).show()
            startActivity(Intent(context, EditAkunAktivity::class.java))
        }
        root.btn_isc.setOnClickListener {
            val intent = Intent(context, TentangISCActivity::class.java)
            startActivity(intent)
        }
        root.cv_b_diproses.setOnClickListener {
            val intent = Intent(context, RiwayatPengaduan::class.java)
            startActivity(intent)
        }
        return root
    }

    fun getRiwayatBelum(status: String, idPengguna: String) {
        val stringRequest = object : StringRequest(
            Method.POST, EndPoint.URL_RIWAYAT,
            Response.Listener<String> { response ->

                try {
                    val obj = JSONObject(response)

                    if (!obj.getBoolean("error")) {

                        val array = obj.getJSONArray("response")
                        for (i in 0 until array.length()) {
                            val riwayatJson = array.getJSONObject(i)

                            val riwayat = RiwayatModel(riwayatJson.getString("count"))

                            belum.text = riwayat.count
                        }
                    } else {
                        Toast.makeText(requireContext(), "GAGAL", Toast.LENGTH_SHORT).show()
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
                params["status"] = status
                params["id_pengguna"] = idPengguna

                return params
            }
        }
        VolleySingleton.getInstance(requireContext()).addToRequestQueue(stringRequest)
    }

    fun getRiwayatSedang(status: String, idPengguna: String) {
        val stringRequest = object : StringRequest(
            Method.POST, EndPoint.URL_RIWAYAT,
            Response.Listener<String> { response ->

                try {
                    val obj = JSONObject(response)

                    if (!obj.getBoolean("error")) {

                        val array = obj.getJSONArray("response")
                        for (i in 0 until array.length()) {
                            val riwayatJson = array.getJSONObject(i)

                            val riwayat = RiwayatModel(riwayatJson.getString("count"))

                            sedang.text = riwayat.count
                        }
                    } else {
                        Toast.makeText(requireContext(), "GAGAL", Toast.LENGTH_SHORT).show()
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
                params["status"] = status
                params["id_pengguna"] = idPengguna

                return params
            }
        }
        VolleySingleton.getInstance(requireContext()).addToRequestQueue(stringRequest)
    }

    fun getRiwayatSelesai(status: String, idPengguna: String) {
        val stringRequest = object : StringRequest(
            Method.POST, EndPoint.URL_RIWAYAT,
            Response.Listener<String> { response ->

                try {
                    val obj = JSONObject(response)

                    if (!obj.getBoolean("error")) {

                        val array = obj.getJSONArray("response")
                        for (i in 0 until array.length()) {
                            val riwayatJson = array.getJSONObject(i)

                            val riwayat = RiwayatModel(riwayatJson.getString("count"))

                            selesai.text = riwayat.count
                        }
                    } else {
                        Toast.makeText(requireContext(), "GAGAL", Toast.LENGTH_SHORT).show()
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
                params["status"] = status
                params["id_pengguna"] = idPengguna

                return params
            }
        }
        VolleySingleton.getInstance(requireContext()).addToRequestQueue(stringRequest)
    }
            fun getRiwayatTolak(status: String, idPengguna: String) {
                val stringRequest = object : StringRequest(
                    Method.POST, EndPoint.URL_RIWAYAT,
                    Response.Listener<String> { response ->

                        try {
                            val obj = JSONObject(response)

                            if (!obj.getBoolean("error")) {

                                val array = obj.getJSONArray("response")
                                for (i in 0 until array.length()) {
                                    val riwayatJson = array.getJSONObject(i)

                                    val riwayat = RiwayatModel(riwayatJson.getString("count"))

                                    tolak.text = riwayat.count
                                }
                            } else {
                                Toast.makeText(requireContext(), "GAGAL", Toast.LENGTH_SHORT).show()
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
                params["status"] = status
                params["id_pengguna"] = idPengguna

                return params
            }
        }
        VolleySingleton.getInstance(requireContext()).addToRequestQueue(stringRequest)
    }

}
