package com.kriswantoro.indramayu.util

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Base64
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.kriswantoro.indramayu.R
import java.io.ByteArrayOutputStream
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * File Utility
 *
 * @author Dhaval Patel
 * @version 1.6
 * @since 05 January 2019
 */
object FileUtil {

    fun BitmapToString(bitmap: Bitmap): String? {
        var bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bytes)
        val imageByte: ByteArray = bytes.toByteArray()
        var imageString: String? = ""

        try {
            System.gc()
            imageString = Base64.encodeToString(imageByte, Base64.DEFAULT)
            Log.i("Success", "Base64-->$imageString")
        } catch (e: OutOfMemoryError) {
            bytes = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bytes)
            imageString = Base64.encodeToString(imageByte, Base64.DEFAULT)
            Log.e("Out Memory", "Compress: $imageString")
        }

        return imageString
    }
}
