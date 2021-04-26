package com.rubearen.zipunzip.basura

import android.Manifest
import android.annotation.TargetApi
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import com.rubearen.zipunzip.R

class Utility {

    private val MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    fun checkPermission(context: Context): Boolean {
        val currentAPIVersion = Build.VERSION.SDK_INT
        if (currentAPIVersion >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(context as Activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    val alertBuilder = AlertDialog.Builder(context)
                    alertBuilder.setCancelable(true)
                    alertBuilder.setTitle(R.string.permission_necessary)
                    alertBuilder.setMessage(R.string.external_storage_permission)
                    alertBuilder.setPositiveButton(android.R.string.yes) { dialog, which -> ActivityCompat.requestPermissions(context, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE) }
                    val alert = alertBuilder.create()
                    alert.show()
                } else {
                    ActivityCompat.requestPermissions(context, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE)
                }
                return false
            } else {
                return true
            }
        } else {
            return true
        }
    }
}