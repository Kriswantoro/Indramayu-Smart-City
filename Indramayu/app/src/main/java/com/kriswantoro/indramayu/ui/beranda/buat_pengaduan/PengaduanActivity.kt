package com.kriswantoro.indramayu.ui.beranda.buat_pengaduan

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.kriswantoro.indramayu.MainActivity
import com.kriswantoro.indramayu.R
import com.kriswantoro.indramayu.util.EndPoint
import com.kriswantoro.indramayu.util.FileUtil
import com.kriswantoro.indramayu.util.VolleySingleton
import kotlinx.android.synthetic.main.activity_pengaduan.*
import kotlinx.android.synthetic.main.activity_pengaduan.btn_ganti_foto
import org.json.JSONException
import org.json.JSONObject
import java.io.File
import java.io.IOException

class PengaduanActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    var judulPengaduan: String = ""
    lateinit var kategoriPengaduan: String
    var pesanPengaduan: String = ""
    var noTelp: String = ""
    var lokasiPengaduan: String = ""
    var statusPengaduan: String = "1"
    private var pathImage: String? = ""
    private var editPhoto: String = ""
    private var fotoPengaduan: String = ""


    private val GALLERY = 1
    private val CAMERA = 2
    private val PERMISSION_CODE = 1001

    private lateinit var spinner: Spinner


    companion object {
        private var arrayAdapter: ArrayAdapter<String>? = null
        private var itemList = arrayOf(
            "Gelandangan dan Pengemis",
            "Sampah",
            "Jalanan dan Rambu Lalulintas",
            "Banjir",
            "Mobilitas dan Akses",
            "Tunawisma/Pengemis",
            "Kaki Lima Liar",
            "PJU Rusak",
            "Tanaman Bermasalah",
            "Fasilitas Umum",
            "Parkir Liar",
            "Pungutan Liar",
            "Pelanggaran Ketertiban",
            "Rambu Jalan",
            "Kebutuhan Sembako",
            "Orang Hilang",
            "Iklan Liar"
        )

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
        arrayAdapter =
            ArrayAdapter(applicationContext, android.R.layout.simple_list_item_1, itemList)
        spinner.adapter = arrayAdapter
        spinner.onItemSelectedListener = this

//        FileUtil.viewImage(this, foto_pengaduan, noTelp)

        btn_buat_pengaduan.setOnClickListener {
            postPengaduan()
            Toast.makeText(this, "Sukses!", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, MainActivity::class.java))
        }

        btn_ganti_foto.setOnClickListener { showPicture() }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALLERY) {
            if (data != null) {
                val contentURI = data.data
                try {
                    val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, contentURI)
                    pathImage = FileUtil.BitmapToString(bitmap)
                    Log.i("Success", "Image from gallery is Ready!")
                    foto_pengaduan.setImageBitmap(bitmap)
                } catch (e: IOException) {
                    e.printStackTrace()
                    Log.e("Error", "Image from gallery is Failure!")
                }
            }
        } else if (requestCode == CAMERA) {
            if (data != null) {
                val thum = data.extras
                try {
                    val thumbnail = thum?.get("data") as Bitmap
                    pathImage = FileUtil.BitmapToString(thumbnail)
                    foto_pengaduan.setImageBitmap(thumbnail)
                    Log.i("Success", "Image from take picture is Ready!")
                } catch (e: IOException) {
                    e.printStackTrace()
                    Log.e("Error", "Image from take picture is Failure!")
                }
            }

        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        kategoriPengaduan = spinner.selectedItem.toString()
        Log.e("kategori", kategoriPengaduan)
//        Toast.makeText(this, kategoriPengaduan, Toast.LENGTH_LONG).show()
    }

    private fun postPengaduan() {
        judulPengaduan = judul_pengaduan.text.toString()
        pesanPengaduan = pesan.text.toString()
        lokasiPengaduan = ed_lokasi_tempat.text.toString()

        if (pathImage != "" && pathImage != null) {
            editPhoto = pathImage.toString().trim()
            fotoPengaduan = ("data:image/jpg;base64,$pathImage").trim()
        }

        val stringRequest = object : StringRequest(
            Method.POST, EndPoint.URL_POST_PENGADUAN,
            Response.Listener<String> { response ->
                try {
                    val obj = JSONObject(response)
                    Toast.makeText(applicationContext, obj.getString("message"), Toast.LENGTH_LONG)
                        .show()
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(
                    applicationContext,
                    error?.message,
                    Toast.LENGTH_LONG
                ).show()
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["judul_pengaduan"] = judulPengaduan
                params["kategori"] = kategoriPengaduan
                params["pesan"] = pesanPengaduan
                params["foto_pengaduan"] = noTelp
                params["lokasi_pengaduan"] = lokasiPengaduan
                params["id_status_pengaduan"] = statusPengaduan
                return params
            }
        }

        //request
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest)
    }

    fun showPicture() {
        val pictureDialog = AlertDialog.Builder(this)
        pictureDialog.setTitle("Option to Access")
        val pic = arrayOf("Choose from Gallery", "Capture by Camera")
        pictureDialog.setItems(pic) { dialog, which ->
            when (which) {
                0 -> choosefromGallery()
                1 -> capturebyCamera()
            }
        }
        pictureDialog.show()
    }

    fun choosefromGallery() {
        val galleryIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        startActivityForResult(galleryIntent, GALLERY)
    }

    fun capturebyCamera() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (this.let { it1 ->
                    ContextCompat.checkSelfPermission(
                        it1,
                        Manifest.permission.CAMERA
                    )
                } ==
                PackageManager.PERMISSION_DENIED) {
                val permissions = arrayOf(Manifest.permission.CAMERA);
                requestPermissions(permissions, PERMISSION_CODE)
            } else {
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(cameraIntent, CAMERA)
            }
        }
    }
}
