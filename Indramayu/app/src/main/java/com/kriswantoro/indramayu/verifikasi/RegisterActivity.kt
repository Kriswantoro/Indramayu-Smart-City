package com.kriswantoro.indramayu.verifikasi

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.toolbox.StringRequest
import com.github.dhaval2404.imagepicker.ImagePicker
import com.github.dhaval2404.imagepicker.sample.setLocalImage
import com.kriswantoro.indramayu.MainActivity
import com.kriswantoro.indramayu.R
import com.kriswantoro.indramayu.util.EndPoint
import com.kriswantoro.indramayu.util.VolleySingleton
import kotlinx.android.synthetic.main.activity_register.*
import org.json.JSONException
import org.json.JSONObject
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
    private lateinit var edtNamaPengguna: EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtNoTlpn: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        foto_profil = findViewById(R.id.foto_profil)
        edtNamaPengguna = findViewById(R.id.nama_lengkap)
        edtEmail = findViewById(R.id.email)
        edtNoTlpn = findViewById(R.id.no_telpon)

        btn_daftar.setOnClickListener {
            registerUser()
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
                PROFILE_IMAGE_REQ_CODE -> {
                    mProfileFile = file
                    foto_profil.setLocalImage(file, true)
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

    private fun registerUser(){
        val namaPengguna = edtNamaPengguna.text.toString()
        val email = edtEmail.text.toString()
        val noTelp = edtNoTlpn.text.toString()
        var fotoProfil = "https://png.pngtree.com/element_our/png/20181206/users-vector-icon-png_260862.jpg"

        val stringRequest = object : StringRequest(
            Method.POST, EndPoint.URL_REGISTER,
            com.android.volley.Response.Listener<String> { response ->
                try {
                    val obj = JSONObject(response)
                    Toast.makeText(applicationContext, obj.getString("message"), Toast.LENGTH_LONG)
                        .show()
                    startActivity(Intent(this, LoginActivity::class.java))
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            com.android.volley.Response.ErrorListener { error ->
                Toast.makeText(
                    applicationContext,
                    error?.message,
                    Toast.LENGTH_LONG
                ).show()
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["foto_pengguna"] = fotoProfil
                params["nama_pengguna"] = namaPengguna
                params["email"] = email
                params["no_tlpn"] = noTelp
                return params
            }
        }

        //request
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest)
    }
}
