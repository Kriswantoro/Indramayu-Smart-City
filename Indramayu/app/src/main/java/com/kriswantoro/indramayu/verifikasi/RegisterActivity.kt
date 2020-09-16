package com.kriswantoro.indramayu.verifikasi

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.android.volley.AuthFailureError
import com.android.volley.toolbox.StringRequest
import com.github.dhaval2404.imagepicker.ImagePicker
import com.github.dhaval2404.imagepicker.sample.setLocalImage
import com.kriswantoro.indramayu.R
import com.kriswantoro.indramayu.util.EndPoint
import com.kriswantoro.indramayu.util.FileUtil
import com.kriswantoro.indramayu.util.VolleySingleton
import com.kriswantoro.indramayu.util.retrofit.model.CheckNikModel
import kotlinx.android.synthetic.main.activity_daftar_n_i_k.*
import kotlinx.android.synthetic.main.activity_pengaduan.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.btn_ganti_foto
import kotlinx.android.synthetic.main.activity_register.buttonMasuk
import org.json.JSONException
import org.json.JSONObject
import java.io.File
import java.io.IOException


class RegisterActivity : AppCompatActivity() {

    private var pathImage: String? = ""
    private val GALLERY = 1
    private val CAMERA = 2
    private val PERMISSION_CODE = 1001

    private lateinit var foto_profil: ImageView
    private lateinit var edtEmail: EditText
    private lateinit var edtNoTlpn: EditText

    var nikUser: String = ""
    var namaUser: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        foto_profil = findViewById(R.id.foto_profil)
        edtEmail = findViewById(R.id.email)
        edtNoTlpn = findViewById(R.id.no_telpon)

        nikUser = nik.text.toString()
        check_nik.setOnClickListener { checkNik() }
        btn_daftar.setOnClickListener { registerUser() }
        buttonMasuk.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
//        daftarnik.setOnClickListener{
//            startActivity(Intent(this, DaftarNIKActivity::class.java))
//        }

        btn_ganti_foto.setOnClickListener { showPicture() }

    }

    fun checkNik() {
        val stringRequest = object : StringRequest(
            Method.POST, EndPoint.URL_CHECK_NIK,
            com.android.volley.Response.Listener<String> { response ->

                try {
                    val obj = JSONObject(response)

                    if (!obj.getBoolean("error")) {

                        val array = obj.getJSONArray("response")
                        for (i in 0 until array.length()) {
                            val nikJson = array.getJSONObject(i)

                            val nik = CheckNikModel(
                                nikJson.getString("nik"),
                                nikJson.getString("nama")
                            )

                            namaUser = nik.namaUser!!
                            nikUser = nik.nikUser!!
                            nama_lengkap.text = namaUser
                            btn_daftar.isEnabled = true

                        }
                    } else {

                        AlertDialog.Builder(this)
                            .setTitle("Peringatan !")
                            .setMessage("Maaf, NIK tidak ditemukan, harap masukkan dengan benar atau Daftarkan NIK Anda")
                            .setPositiveButton("Ok", { dialog, which -> })
                            .setCancelable(true)
                            .show()

                        nik.text.clear()
                        btn_daftar.isEnabled = false
                        Toast.makeText(this@RegisterActivity, obj.getString("response"), Toast.LENGTH_SHORT).show()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            com.android.volley.Response.ErrorListener { error ->
                Toast.makeText(applicationContext, error.message, Toast.LENGTH_SHORT).show()
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["nik"] = nik.text.toString()

                return params
            }
        }
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest)
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
                    foto_profil.setImageBitmap(bitmap)
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
                    foto_profil.setImageBitmap(thumbnail)
                    Log.i("Success", "Image from take picture is Ready!")
                } catch (e: IOException) {
                    e.printStackTrace()
                    Log.e("Error", "Image from take picture is Failure!")
                }
            }

        }
    }

    fun showPicture() {
        val pictureDialog = AlertDialog.Builder(this)
        pictureDialog.setTitle("Option to Access")
        val pic = arrayOf("Capture by Camera")
        pictureDialog.setItems(pic) { dialog, which ->
            when (which) {
                0 -> capturebyCamera()
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

    private fun registerUser() {
        val email = edtEmail.text.toString()
        val noTelp = edtNoTlpn.text.toString()

        val stringRequest = object : StringRequest(
            Method.POST, EndPoint.URL_REGISTER,
            com.android.volley.Response.Listener<String> { response ->
                try {
                    val obj = JSONObject(response)
                    when {
                        obj.getInt("code") == 200 -> {
                            Toast.makeText(
                                applicationContext,
                                "Register Berhasil",
                                Toast.LENGTH_LONG
                            )
                                .show()
                            startActivity(Intent(this, LoginActivity::class.java))
                        }
                        obj.getInt("code") == 400 -> {
                            Toast.makeText(
                                applicationContext,
                                "No Telfon atau NIK anda sudah terdaftar",
                                Toast.LENGTH_LONG
                            )
                                .show()
                        }
                        else -> Toast.makeText(
                            applicationContext,
                            obj.getString("message"),
                            Toast.LENGTH_LONG
                        )
                            .show()
                    }

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
                params["nik"] = nikUser
                params["foto_pengguna"] = ("data:image/jpeg;base64," + pathImage!!)
                params["nama_pengguna"] = namaUser
                params["email"] = email
                params["no_tlpn"] = noTelp
                params["status_pengguna"] = ""
                return params
            }
        }

        //request
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest)
    }
}
