package com.kriswantoro.indramayu.util

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class SaveImageHelper {

    companion object{
        private val TAG = "saveimage"
        val IMAGE_PERMISSION = 99

        fun requestSavePermission(caller: Activity){
            val permissionList = ArrayList<String>()

            if (ContextCompat.checkSelfPermission(caller, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }

            if (ContextCompat.checkSelfPermission(caller, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                permissionList.add(Manifest.permission.READ_EXTERNAL_STORAGE)
            }

            if (permissionList.size > 0) {
                val permissionArray = arrayOfNulls<String>(permissionList.size)

                for (i in permissionList.indices) {
                    permissionArray[i] = permissionList[i]
                }

                ActivityCompat.requestPermissions(caller,
                    permissionArray,
                    IMAGE_PERMISSION)
            }
        }

        fun haveSavePermission(caller: Activity): Boolean {
            var permissionCheck = ContextCompat.checkSelfPermission(caller,
                Manifest.permission.READ_EXTERNAL_STORAGE)

            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                permissionCheck = ContextCompat.checkSelfPermission(caller,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)

                if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                    return true
                }
            }

            return false
        }
    }
}