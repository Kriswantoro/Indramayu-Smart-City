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

    /**
     * @param file File
     * @return Image Info
     */
    fun getFileInfo(file: File?): String {
        if (file == null || !file.exists()) {
            return "Image not found"
        }

        val resolution = getImageResolution(file)
        val sdf = SimpleDateFormat("dd/MM/yyyy hh:mm:ss a", Locale.getDefault())
        val modified = sdf.format(file.lastModified())
        return StringBuilder()

            .append("Resolution: ")
            .append("${resolution.first}x${resolution.second}")
            .append("\n\n")

            .append("Modified: ")
            .append(modified)
            .append("\n\n")

            .append("File Size: ")
            .append(getFileSize(file))
            .append("\n\n")

            .append("File Path: ")
            .append(file.absolutePath)
            .toString()
    }

    private fun getFileSize(file: File): String {
        val fileSize = file.length().toFloat()
        val mb = fileSize / (1024 * 1024)
        val kb = fileSize / (1024)

        return if (mb > 1) {
            "$mb MB"
        } else {
            "$kb KB"
        }
    }

    private fun getImageResolution(file: File): Pair<Int, Int> {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(file.absolutePath, options)
        return Pair(options.outWidth, options.outHeight)
    }

    fun BitmapToString(bitmap: Bitmap): String? {
        var bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bytes)
        val imageByte: ByteArray = bytes.toByteArray()
        var imageString: String? = null

        try {
            System.gc()
            imageString = Base64.encodeToString(imageByte, Base64.DEFAULT)
            Log.i("Success", "Base64-->" + imageString)
        } catch (e: OutOfMemoryError) {
            bytes = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bytes)
            imageString = Base64.encodeToString(imageByte, Base64.DEFAULT)
            Log.e("Out Memory", "Compress: " + imageString)
        }

        return imageString
    }

    fun viewImage(context: Context, imageView: ImageView, titleImage: String) {
        val url = "${EndPoint.BASE}assets/images/user/$titleImage.png"
        Glide.with(context)
            .load(url)
            .placeholder(R.drawable.ic_panorama_black_24dp)
            .error(R.drawable.ic_panorama_black_24dp)
            .skipMemoryCache(true) //2
            .diskCacheStrategy(DiskCacheStrategy.NONE) //3
            .transform(CircleCrop()) //4
            .into(imageView)
    }

    fun ContentResolver.getFileName(fileUri: Uri): String {
        var name = ""
        val returnCursor = this.query(fileUri, null, null, null, null)
        if (returnCursor != null) {
            val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            returnCursor.moveToFirst()
            name = returnCursor.getString(nameIndex)
            returnCursor.close()
        }
        return name
    }
}
