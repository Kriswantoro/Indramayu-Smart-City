package com.kriswantoro.indramayu.ui.akun.edit_akun

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.github.dhaval2404.imagepicker.ImagePicker
import com.github.dhaval2404.imagepicker.sample.setDrawableImage
import com.github.dhaval2404.imagepicker.sample.setLocalImage
import com.kriswantoro.indramayu.MainActivity
import com.kriswantoro.indramayu.R
import com.kriswantoro.indramayu.intro.SharedPref
import com.kriswantoro.indramayu.ui.akun.AkunFragment
import com.kriswantoro.indramayu.ui.akun.AkunModel
import com.kriswantoro.indramayu.util.EndPoint
import com.kriswantoro.indramayu.util.FileUtil
import com.kriswantoro.indramayu.util.VolleySingleton
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_edit_akun.*
import kotlinx.android.synthetic.main.activity_pengaduan.*
import org.json.JSONException
import org.json.JSONObject
import java.io.File
import java.io.IOException

class EditAkunAktivity : AppCompatActivity() {
    private val GALLERY = 1
    private val CAMERA = 2
    private val PERMISSION_CODE = 1001

    private var pathImage: String? = ""

    var idPengguna: String = ""
    var fotoPengguna: String = ""
    var noTlpn: String = ""
    var nama: String = ""
    var email: String = ""
    var status: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_akun)


        if (SharedPref.getInstance(this).isLoggedIn) {

            val user = SharedPref.getInstance(this).user

            val base64String = user.fotoPengguna
            val pureBase64Encoded = base64String?.substring(base64String.indexOf(",") + 1)
            val imageBytes = Base64.decode(pureBase64Encoded, Base64.DEFAULT)
            val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

            idPengguna = user.idPengguna.toString()
            fotoPengguna = user.fotoPengguna.toString()
            nama = user.namaPengguna.toString()
            email = user.email.toString()
            noTlpn = user.noTlpn.toString()
            status = user.status.toString()

            if (user.fotoPengguna.toString().isEmpty()) {
                foto_profil.setDrawableImage(R.drawable.foto_profile, true)
            } else
                foto_profil.setImageBitmap(decodedImage)
        }

        val gantiFoto: View = findViewById(R.id.btn_ganti_foto)

        gantiFoto.setOnClickListener {
            showPicture()
        }
        et_nomor_telepon.setText(noTlpn)

        btn_simpan.setOnClickListener { editProfile(idPengguna, fotoPengguna, nama, email) }

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
                    foto_profil.setImageBitmap(thumbnail)
                    Log.i("Success", "Image from take picture is Ready!")
                } catch (e: IOException) {
                    e.printStackTrace()
                    Log.e("Error", "Image from take picture is Failure!")
                }
            }
        }
    }

    fun editProfile(id: String, foto: String, nama: String, email: String) {

        var image: String = foto

        image = if (pathImage!!.isEmpty()) {
            fotoPengguna
        } else{
            ("data:image/jpeg;base64," + pathImage!!)
        }

        noTlpn = et_nomor_telepon.text.toString()

        val stringRequest = object : StringRequest(
            Method.POST, EndPoint.URL_UPDATE_USER,
            Response.Listener<String> { response ->
                try {
                    val obj = JSONObject(response)
                    if (obj.getInt("code") == 200) {
                        Toast.makeText(
                            applicationContext,
                            obj.getString("message"),
                            Toast.LENGTH_LONG
                        )
                            .show()
                        Toast.makeText(this, "Sukses!", Toast.LENGTH_LONG).show()
                        SharedPref.getInstance(applicationContext).userLogin(user = AkunModel(id, image, nama, email, noTlpn, status))
                        startActivity(Intent(this, MainActivity::class.java))
                    } else if (obj.getInt("code") == 300) {
                        Toast.makeText(
                            applicationContext,
                            obj.getString("message"),
                            Toast.LENGTH_LONG
                        )
                            .show()
                        Toast.makeText(this, "Error!", Toast.LENGTH_LONG).show()
                    }
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
                params["id_pengguna"] = id
                params["foto_pengguna"] = image
                params["no_tlpn"] = noTlpn
                return params
            }

        }

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest)
    }

}
