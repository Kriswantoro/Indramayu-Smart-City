package com.kriswantoro.indramayu.util

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class PermissionHelper {

    companion object{
        val IMAGE_PERMISSION = 99
        fun requestSavePermission(caller: Activity){
            val permissionList = ArrayList<String>()

            if (ActivityCompat.checkSelfPermission(caller, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION)
            }

            if (ActivityCompat.checkSelfPermission(caller, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                permissionList.add(Manifest.permission.ACCESS_COARSE_LOCATION)
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
            var permissionCheck = ActivityCompat.checkSelfPermission(caller,
                Manifest.permission.ACCESS_FINE_LOCATION)

            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                permissionCheck = ActivityCompat.checkSelfPermission(caller,
                    Manifest.permission.ACCESS_COARSE_LOCATION)

                if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                    return true
                }
            }

            return false
        }
    }
}