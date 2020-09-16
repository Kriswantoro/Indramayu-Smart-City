package com.kriswantoro.indramayu.ui.akun

import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.kriswantoro.indramayu.R
import com.kriswantoro.indramayu.intro.SharedPref
import com.kriswantoro.indramayu.ui.akun.edit_akun.EditAkunAktivity
import com.kriswantoro.indramayu.util.EndPoint
import com.kriswantoro.indramayu.util.VolleySingleton
import com.kriswantoro.indramayu.verifikasi.LoginActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_akun.*
import kotlinx.android.synthetic.main.fragment_akun.view.*
import org.json.JSONException
import org.json.JSONObject

class AkunFragment : Fragment() {

    private var barChart: BarChart? = null
//    private val seekBarX: SeekBar? = null, private  var seekBarY:SeekBar? = null
//    private val tvX: TextView? = null, private  var tvY:TextView? = null

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
        barChart = root.findViewById(R.id.barchart)

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
            startActivity(Intent(context, EditAkunAktivity::class.java))
        }

        val barEntries1 = ArrayList<BarEntry>()
        val barEntries2 = ArrayList<BarEntry>()
        val barEntries3 = ArrayList<BarEntry>()
        val barEntries4 = ArrayList<BarEntry>()

        barEntries1.add(BarEntry(1f, 250f))
        barEntries1.add(BarEntry(1f, 150f))
        barEntries1.add(BarEntry(1f, 100f))
        barEntries1.add(BarEntry(1f, 200f))
        barEntries1.add(BarEntry(1f, 160f))

        barEntries2.add(BarEntry(2f, 182f))
        barEntries2.add(BarEntry(2f, 182f))
        barEntries2.add(BarEntry(2f, 182f))
        barEntries2.add(BarEntry(2f, 182f))
        barEntries2.add(BarEntry(2f, 182f))

        barEntries3.add(BarEntry(3f, 182f))
        barEntries3.add(BarEntry(3f, 182f))
        barEntries3.add(BarEntry(3f, 182f))
        barEntries3.add(BarEntry(3f, 182f))
        barEntries3.add(BarEntry(3f, 182f))

        barEntries4.add(BarEntry(4f, 250f))
        barEntries4.add(BarEntry(4f, 150f))
        barEntries4.add(BarEntry(4f, 100f))
        barEntries4.add(BarEntry(4f, 200f))
        barEntries4.add(BarEntry(4f, 160f))

        val barDataSet1 = BarDataSet(barEntries1, "Belum diproses")
        barDataSet1.color = Color.parseColor("#616161")
        val barDataSet2 = BarDataSet(barEntries2, "Sedang diproses")
        barDataSet2.color = Color.parseColor("#FFFFEB3B")
        val barDataSet3 = BarDataSet(barEntries3, "Selsai") //bagian kene sing error e
        barDataSet3.color = Color.parseColor("#00FF00")
        val barDataSet4 = BarDataSet(barEntries4, "Ditolak") // bagian kene kedik
        barDataSet4.color = Color.parseColor("#FF0000")

        barChart?.animateY(3000)
        val year = arrayOf("2018", "2019", "2020", "2021")
        val data = BarData(barDataSet1, barDataSet2, barDataSet3, barDataSet4)
        barChart?.data = data


        val xAxis = barChart?.xAxis
        xAxis?.setCenterAxisLabels(true)
        xAxis?.position = XAxis.XAxisPosition.BOTTOM
        xAxis?.setDrawGridLines(false)
        xAxis?.granularity = 1.1666f // only intervals of 1 day
        xAxis?.axisMinimum = 1f

        val leftAxis: YAxis? = barChart?.axisLeft
        leftAxis?.textColor = Color.BLACK
        leftAxis?.setDrawGridLines(false)
        leftAxis?.granularity = 2f
        leftAxis?.setLabelCount(8, true)

        barChart?.setFitBars(true)
        barChart?.axisRight?.isEnabled = false
        barChart?.setScaleEnabled(false)
        barChart?.description?.isEnabled = false
        barChart?.setPinchZoom(false)
        barChart?.setDrawGridBackground(false)


        xAxis?.valueFormatter = IndexAxisValueFormatter(year)
        barChart?.axisLeft?.axisMinimum = 0f
        val barSpace = 0f
        val groupSpace = 0.35f
        val groupCount = 4

        //IMPORTANT ***
        data.barWidth = 0.2f
        barChart?.xAxis?.axisMinimum = 0f
        barChart?.xAxis?.axisMaximum = 0 + barChart?.barData?.getGroupWidth(groupSpace, barSpace) !!* groupCount
        barChart?.groupBars(0f, groupSpace, barSpace)
        barChart?.invalidate()

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

                            cv_b_diproses.setOnClickListener {
                                val intent = Intent(context, RiwayatPengaduan::class.java)
                                intent.putExtra("status", status)
                                startActivity(intent)
                            }

                        }
                    } else {
                        Toast.makeText(requireContext(), "Data Kosong", Toast.LENGTH_SHORT).show()
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
                            cv_s_diproses.setOnClickListener {
                                val intent = Intent(context, RiwayatPengaduan::class.java)
                                intent.putExtra("status", status)
                                startActivity(intent)
                            }
                        }
                    } else {
                        Toast.makeText(requireContext(), "Data Kosong", Toast.LENGTH_SHORT).show()
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
                            cv_selesai.setOnClickListener {
                                val intent = Intent(context, RiwayatPengaduan::class.java)
                                intent.putExtra("status", status)
                                startActivity(intent)
                            }
                        }
                    } else {
                        Toast.makeText(requireContext(), "Data Kosong", Toast.LENGTH_SHORT).show()
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
                                    cv_tolak.setOnClickListener {
                                        val intent = Intent(context, RiwayatPengaduan::class.java)
                                        intent.putExtra("status", status)
                                        startActivity(intent)
                                    }
                                }
                            } else {
                                Toast.makeText(requireContext(), "Data Kosong", Toast.LENGTH_SHORT).show()
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
