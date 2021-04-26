package com.rubearen.zipunzip

import android.os.Bundle
import android.os.Environment

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.rubearen.zipunzip.utils.FileHelper
import com.rubearen.zipunzip.basura.Utility

class MainActivity : AppCompatActivity() {

    private val SDPath = Environment.getExternalStorageDirectory().absolutePath
    private val dataPath = "$SDPath/Zara/zipunzipFile/data/"
    private val zipPath = "$SDPath/Zara/zipunzipFile/zip/"
    private val unzipPath = "$SDPath/Zara/zipunzipFile/unzip/"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Check for permission
        Utility().checkPermission(this)

        //Create dummy files
        FileHelper.saveToFile(dataPath, "This is AndroidCodility sample data 01", "dummy1.txt")
        FileHelper.saveToFile(dataPath, "This is AndroidCodility sample data 02", "dummy2.txt")
    }

    fun zipView(view: View) {
        ZipUnzipIntentService.startActionZip(this, dataPath, zipPath)
    }

    fun unZipView(view: View) {
        ZipUnzipIntentService.startActionUnzip(this, "$zipPath/zip", unzipPath)
    }
}