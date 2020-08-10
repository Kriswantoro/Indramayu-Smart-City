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
import com.google.gson.Gson
import com.kriswantoro.indramayu.MainActivity
import com.kriswantoro.indramayu.R
import com.kriswantoro.indramayu.intro.SharedPref
import com.kriswantoro.indramayu.util.EndPoint
import com.kriswantoro.indramayu.util.FileUtil
import com.kriswantoro.indramayu.util.VolleySingleton
import com.kriswantoro.indramayu.util.retrofit.network.BaseService
import com.kriswantoro.indramayu.util.retrofit.network.response.BaseResponse
import com.kriswantoro.indramayu.verifikasi.LoginActivity
import kotlinx.android.synthetic.main.activity_pengaduan.*
import kotlinx.android.synthetic.main.activity_pengaduan.btn_ganti_foto
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import java.io.File
import java.io.IOException

class PengaduanActivity : AppCompatActivity(){

    var judulPengaduan: String = ""
    var pesanPengaduan: String = ""
    var noTelp: String = ""
    var lokasiPengaduan: String = ""
    var statusPengaduan: String = "Belum Diproses"
    private var pathImage: String? = ""
    private var editPhoto: String = ""
    var idPengguna: String = ""
    val mListKategori = ArrayList<KategoriPengaduanModel>()

    var listSpinnerIdDinas = ""
    var listDataSpinner = ""


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


//        FileUtil.viewImage(this, foto_pengaduan, noTelp)

        getKategoriPengaduan()

        btn_buat_pengaduan.setOnClickListener {

            if (SharedPref.getInstance(this).isLoggedIn) {

                val user = SharedPref.getInstance(this).user

                idPengguna = user.idPengguna.toString()
                postPengaduan()
                Toast.makeText(this, "Sukses!", Toast.LENGTH_LONG).show()
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                Toast.makeText(this, "You're not Login", Toast.LENGTH_LONG).show()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
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

    private fun postPengaduan() {
        judulPengaduan = judul_pengaduan.text.toString()
        pesanPengaduan = pesan.text.toString()
        lokasiPengaduan = ed_lokasi_tempat.text.toString()

        val fotoPengaduan =
            "https://png.pngtree.com/element_our/png/20181206/users-vector-icon-png_260862.jpg"


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
                params["id_pengguna"] = idPengguna
                params["id_dinas"] = listSpinnerIdDinas
                params["judul_pengaduan"] = judulPengaduan
                params["kategori"] = listDataSpinner
                params["pesan"] = pesanPengaduan
                params["foto_pengaduan"] = fotoPengaduan
                params["lokasi"] = lokasiPengaduan
                params["status"] = statusPengaduan
                params["status_kirim"] = ""
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

    fun getKategoriPengaduan() {
        BaseService.getKategoriPengaduanService().getKategoriPengaduan("getkategoripengaduan")
            .enqueue(object : Callback<BaseResponse<KategoriPengaduanModel>> {
                override fun onFailure(
                    call: Call<BaseResponse<KategoriPengaduanModel>>,
                    t: Throwable
                ) {
                    Toast.makeText(this@PengaduanActivity, t.message, Toast.LENGTH_LONG).show()
                }

                override fun onResponse(
                    call: Call<BaseResponse<KategoriPengaduanModel>>,
                    response: retrofit2.Response<BaseResponse<KategoriPengaduanModel>>
                ) {
                    val responseData = response.body()?.response
                    if (response.body()?.code == 200) {
                        val saveData = Gson().toJson(responseData)

                        mListKategori.addAll(responseData!!.toList())

                        spinnerKategori(mListKategori)

                        Log.e("listKategori", saveData.toString())
                    }
                }
            })
    }

    fun spinnerKategori(data: ArrayList<KategoriPengaduanModel>){

        /* kategori Type Spinner */
        val sizeKategori = data.size
        val spinnerKategoriItems = arrayOfNulls<String>(sizeKategori)
        val spinnerKategoriValues = arrayOfNulls<String>(sizeKategori)
        val spinnerKategoriPost = arrayOfNulls<String>(sizeKategori)

        if (sizeKategori > 0) {
            for (i in 0 until data.size) {
                spinnerKategoriItems[i] = data[i].namaKategoriPengaduan
                spinnerKategoriValues[i] = data[i].namaDinas
                spinnerKategoriPost[i] = data[i].idDinas.toString()
            }
        }

        val spinnerKategoriAdapter = ArrayAdapter(
            this@PengaduanActivity,
            android.R.layout.simple_spinner_dropdown_item,
            spinnerKategoriItems
        )

        spinner.adapter = spinnerKategoriAdapter
        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val position = spinner.selectedItemPosition
                listDataSpinner = spinnerKategoriItems[position].toString()
                nama_instansi.text = spinnerKategoriValues[position].toString()
                listSpinnerIdDinas = spinnerKategoriPost[position].toString()
                Toast.makeText(this@PengaduanActivity, "$listSpinnerIdDinas $listDataSpinner", Toast.LENGTH_SHORT).show()
            }

        }


    }
}
