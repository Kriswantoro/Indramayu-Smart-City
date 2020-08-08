package com.kriswantoro.indramayu.verifikasi

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.toolbox.StringRequest
import com.github.dhaval2404.imagepicker.ImagePicker
import com.github.dhaval2404.imagepicker.sample.setLocalImage
import com.kriswantoro.indramayu.MainActivity
import com.kriswantoro.indramayu.R
import com.kriswantoro.indramayu.intro.SharedPref
import com.kriswantoro.indramayu.ui.akun.AkunModel
import com.kriswantoro.indramayu.util.EndPoint
import com.kriswantoro.indramayu.util.VolleySingleton
import com.kriswantoro.indramayu.util.retrofit.model.CheckNikModel
import com.kriswantoro.indramayu.util.retrofit.network.BaseService
import com.kriswantoro.indramayu.util.retrofit.network.response.BaseResponse
import kotlinx.android.synthetic.main.activity_register.*
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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
    private lateinit var edtEmail: EditText
    private lateinit var edtNoTlpn: EditText

    val mNikCheck = ArrayList<CheckNikModel>()
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

    }

//    fun checkNik(userNik: String){
//        BaseService.checkNik().checkNik("checkNik", userNik)
//            .enqueue(object : Callback<BaseResponse<CheckNikModel>>{
//                override fun onFailure(call: Call<BaseResponse<CheckNikModel>>, t: Throwable) {
//                    //nothing
//                }
//
//                override fun onResponse(
//                    call: Call<BaseResponse<CheckNikModel>>,
//                    response: Response<BaseResponse<CheckNikModel>>
//                ) {
//                    val responseData = response.body()?.response
//                    if (response.body()?.code == 200) {
//                        mNikCheck.addAll(responseData!!.toList())
//                        namaUser = responseData[0].namaUser
//                        nikUser = responseData[0].nikUser
//                        nama_lengkap.text = namaUser
//                        btn_daftar.isEnabled = true
//                    } else if (response.body()?.code == 302){
//                        nik.text.clear()
//                        btn_daftar.isEnabled = false
//                        Toast.makeText(this@RegisterActivity, response.body()?.response.toString(), Toast.LENGTH_SHORT).show()
//                    }
//                }
//
//            })
//    }

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

//                            Toast.makeText(this, "Welcome to ISC", Toast.LENGTH_LONG).show()
                        }
                    } else {

                        //Toast.makeText(applicationContext, "Kode Pendonor tidak ditemukan, harap masukkan dengan benar", Toast.LENGTH_SHORT).show()
                        AlertDialog.Builder(this)
                            .setTitle("Peringatan !")
                            .setMessage("Maaf, NIK tidak ditemukan, harap masukkan dengan benar")
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

    fun pickProfileImage(view: View) {
        ImagePicker.with(this)
            // Crop Square image
            .cropSquare()
            .setImageProviderInterceptor { imageProvider -> // Intercept ImageProvider
                Log.d("ImagePicker", "Selected ImageProvider: " + imageProvider.name)
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

    private fun registerUser() {
        val email = edtEmail.text.toString()
        val noTelp = edtNoTlpn.text.toString()

        val fotoProfil =
            "https://png.pngtree.com/element_our/png/20181206/users-vector-icon-png_260862.jpg"

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
                params["foto_pengguna"] = fotoProfil
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
