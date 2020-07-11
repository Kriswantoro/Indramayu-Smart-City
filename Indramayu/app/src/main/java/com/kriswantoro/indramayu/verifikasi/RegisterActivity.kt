package com.kriswantoro.indramayu.verifikasi

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONArrayRequestListener
import com.github.dhaval2404.imagepicker.ImagePicker
import com.github.dhaval2404.imagepicker.sample.setLocalImage
import com.kriswantoro.indramayu.MainActivity
import com.kriswantoro.indramayu.R
import com.kriswantoro.indramayu.ui.beranda.buat_pengaduan.PengaduanActivity
import kotlinx.android.synthetic.main.activity_pengaduan.*
import kotlinx.android.synthetic.main.activity_register.*
import okhttp3.Response
import org.json.JSONArray
import java.io.File


class RegisterActivity : AppCompatActivity() {
    companion object {

        private const val GITHUB_REPOSITORY = "https://github.com/Dhaval2404/ImagePicker"

        private const val PROFILE_IMAGE_REQ_CODE = 101
        private const val GALLERY_IMAGE_REQ_CODE = 102
        private const val CAMERA_IMAGE_REQ_CODE = 103
    }

    private var mCameraFile: File? = null
    private var mGalleryFile: File? = null
    private var mProfileFile: File? = null

    private lateinit var foto_profil: ImageView
    private lateinit var nama_lengkap: EditText
    private lateinit var email: EditText
    private lateinit var no_telpon: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        foto_profil = findViewById(R.id.foto_profil)
        nama_lengkap = findViewById(R.id.nama_lengkap)
        email = findViewById(R.id.email)
        no_telpon = findViewById(R.id.no_telpon)

        btn_daftar.setOnClickListener {

            AndroidNetworking.post("http://192.168.100.5:8000/register/")
                .addBodyParameter("nama_pengguna", nama_lengkap.text.toString())
                .addBodyParameter("email", email.text.toString())
                .addBodyParameter("no_tlpn", no_telpon.text.toString())
                .addBodyParameter("foto_pengguna", foto_profil.toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(object : JSONArrayRequestListener{
                    override fun onResponse(response: JSONArray?) {

                        Toast.makeText(this@RegisterActivity,"Data Telah Disimpan", Toast.LENGTH_LONG).show()
                    }

                    override fun onError(anError: ANError?) {
                        Toast.makeText(this@RegisterActivity,"Tidak Ada Jaringan", Toast.LENGTH_LONG).show()
                    }
                })

            startActivity(Intent(this, MainActivity::class.java))
        }
        buttonMasuk.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    fun pickProfileImage(view: View) {ImagePicker.with(this)
        // Crop Square image
        .cropSquare()
        .setImageProviderInterceptor { imageProvider -> // Intercept ImageProvider
            Log.d("ImagePicker", "Selected ImageProvider: "+imageProvider.name)
        }
        // Image resolution will be less than 512 x 512
        .maxResultSize(512, 512)
        .start(PROFILE_IMAGE_REQ_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            Log.e("TAG", "Path:${ImagePicker.getFilePath(data)}")
            // File object will not be null for RESULT_OK
            val file = ImagePicker.getFile(data)!!
            when (requestCode) {
                RegisterActivity.PROFILE_IMAGE_REQ_CODE -> {
                    mProfileFile = file
                    foto_profil.setLocalImage(file, true)
                }
                RegisterActivity.GALLERY_IMAGE_REQ_CODE -> {
                    mGalleryFile = file
                }
                RegisterActivity.CAMERA_IMAGE_REQ_CODE -> {
                    mCameraFile = file
                }
            }
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }
}
