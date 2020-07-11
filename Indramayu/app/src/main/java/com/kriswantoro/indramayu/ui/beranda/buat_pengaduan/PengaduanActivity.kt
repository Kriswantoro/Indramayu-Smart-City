package com.kriswantoro.indramayu.ui.beranda.buat_pengaduan

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.github.dhaval2404.imagepicker.ImagePicker
import com.github.dhaval2404.imagepicker.sample.setDrawableImage
import com.github.dhaval2404.imagepicker.sample.setLocalImage
import com.kriswantoro.indramayu.R
import com.kriswantoro.indramayu.util.EndPoint
import com.kriswantoro.indramayu.util.VolleySingleton
import kotlinx.android.synthetic.main.activity_pengaduan.*
import kotlinx.android.synthetic.main.activity_pengaduan.btn_ganti_foto
import kotlinx.android.synthetic.main.activity_register.*
import org.json.JSONException
import org.json.JSONObject
import java.io.File

class PengaduanActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    var judulPengaduan: String = ""
    lateinit var kategoriPengaduan: String
    var pesanPengaduan: String = ""
    var noTelp: String = ""
    var lokasiPengaduan: String = ""

    private lateinit var spinner : Spinner


    companion object {
        private var arrayAdapter : ArrayAdapter<String>? = null
        private var itemList = arrayOf( "Gelandangan dan Pengemis", "Sampah", "Jalanan dan Rambu Lalulintas", "Banjir", "Mobilitas dan Akses", "Tunawisma/Pengemis", "Kaki Lima Liar", "PJU Rusak", "Tanaman Bermasalah", "Fasilitas Umum", "Parkir Liar", "Pungutan Liar", "Pelanggaran Ketertiban", "Rambu Jalan", "Kebutuhan Sembako", "Orang Hilang", "Iklan Liar")

        private const val GITHUB_REPOSITORY = "https://github.com/Dhaval2404/ImagePicker"

        private const val PROFILE_IMAGE_REQ_CODE = 101
        private const val GALLERY_IMAGE_REQ_CODE = 102
        private const val CAMERA_IMAGE_REQ_CODE = 103
    }

    private var mCameraFile: File? = null
    private var mGalleryFile: File? = null
    private var mProfileFile: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pengaduan)
        spinner = findViewById(R.id.spin_kategori_pengaduan)
        arrayAdapter = ArrayAdapter(applicationContext, android.R.layout.simple_list_item_1, itemList)
        spinner.adapter = arrayAdapter
        spinner.onItemSelectedListener = this

        btn_buat_pengaduan.setOnClickListener {
            postPengaduan()
            Toast.makeText(this, "Sukses!", Toast.LENGTH_LONG).show()
        }

        btn_ganti_foto.setOnClickListener { ImagePicker.with(this)
            // Crop Square image
            .crop(85f, 53f)
            .setImageProviderInterceptor { imageProvider -> // Intercept ImageProvider
                Log.d("ImagePicker", "Selected ImageProvider: "+imageProvider.name)
            }
            // Image resolution will be less than 512 x 512
            .maxResultSize(512, 512)
            .start(PROFILE_IMAGE_REQ_CODE) }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            Log.e("TAG", "Path:${ImagePicker.getFilePath(data)}")
            // File object will not be null for RESULT_OK
            val file = ImagePicker.getFile(data)!!
            when (requestCode) {
                PROFILE_IMAGE_REQ_CODE -> {
                    mProfileFile = file
                    foto_pengaduan.setLocalImage(file, true)
                }
                GALLERY_IMAGE_REQ_CODE -> {
                    mGalleryFile = file
                }
                CAMERA_IMAGE_REQ_CODE -> {
                    mCameraFile = file
                }
            }
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        kategoriPengaduan = spinner.selectedItem.toString()
        Log.e("kategori", kategoriPengaduan)
//        Toast.makeText(this, kategoriPengaduan, Toast.LENGTH_LONG).show()
    }

    private fun postPengaduan(){
        judulPengaduan = judul_pengaduan.text.toString()
        pesanPengaduan = pesan.text.toString()
        noTelp = no_tlpn.text.toString()
        lokasiPengaduan = ed_lokasi_tempat.text.toString()

        val stringRequest = object : StringRequest(Request.Method.POST, EndPoint.URL_POST_PENGADUAN,
            Response.Listener<String> {response ->
                try {
                    val obj = JSONObject(response)
                    Toast.makeText(applicationContext, obj.getString("message"), Toast.LENGTH_LONG).show()
                } catch (e: JSONException){
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error -> Toast.makeText(applicationContext, error?.message, Toast.LENGTH_LONG).show() }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["judul_pengaduan"] = judulPengaduan
                params["kategori"] = kategoriPengaduan
                params["pesan"] = pesanPengaduan
                params["foto_pengaduan"] = noTelp
                params["lokasi_pengaduan"] = lokasiPengaduan
                return params
            }
        }

        //request
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest)
    }
}
