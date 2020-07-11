package com.kriswantoro.indramayu.ui.akun.edit_akun

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.github.dhaval2404.imagepicker.ImagePicker
import com.github.dhaval2404.imagepicker.sample.setDrawableImage
import com.github.dhaval2404.imagepicker.sample.setLocalImage
import com.kriswantoro.indramayu.R
import kotlinx.android.synthetic.main.activity_edit_akun.*
import java.io.File

class EditAkunAktivity : AppCompatActivity() {

    companion object {

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
        setContentView(R.layout.activity_edit_akun)

        foto_profil.setDrawableImage(R.drawable.foto_profile, true)


//        Picasso.get()
//            .load("https://non-indonesia-distribution.brta.in/2018-08/ce8014b02db258d883f545cc27bf4b35.jpg")
//            .into(foto_profil)
    }

    fun pickProfileImage(view: View) {
        ImagePicker.with(this)
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


}
