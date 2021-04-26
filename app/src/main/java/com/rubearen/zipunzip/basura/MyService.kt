package com.rubearen.zipunzip.basura

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Environment
import android.os.IBinder

import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.rubearen.zipunzip.MainActivity
import com.rubearen.zipunzip.R
import com.rubearen.zipunzip.utils.FileHelper

class MyService : Service() {

    private val SDPath = Environment.getExternalStorageDirectory().absolutePath
    private val dataPath = "$SDPath/AndroidCodility/zipunzipFile/data/"
    private val zipPath = "$SDPath/AndroidCodility/zipunzipFile/zip/"


    companion object {
        const val CHANNEL_ID = "ForegroundService Kotlin"

        fun startService(context: Context, message: String) {
            val startIntent = Intent(context, MyService::class.java)
            startIntent.putExtra("inputExtra", message)
            ContextCompat.startForegroundService(context, startIntent)
        }

        fun stopService(context: Context) {
            val stopIntent = Intent(context, MyService::class.java)
            context.stopService(stopIntent)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        //do heavy work on a background thread
        val input: String? = intent?.getStringExtra("inputExtra")
        createNotificationChannel()
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent: PendingIntent = PendingIntent.getActivity(
                this,
                0, notificationIntent, 0
        )
        val notification: Notification = NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(getText(R.string.service_title))
                .setContentText(input)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentIntent(pendingIntent)
                .build()
        startForeground(1, notification)

        if (FileHelper.zip(dataPath, zipPath, "dummy.zip", true)) {
            Toast.makeText(applicationContext, "Zip successfully.", Toast.LENGTH_LONG).show()
            stopSelf()
        }
        else{
            //TODO si falla la compresión
            stopSelf()
        }
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                    CHANNEL_ID, "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager: NotificationManager? = getSystemService(NotificationManager::class.java)
            manager!!.createNotificationChannel(serviceChannel)
        }
    }
}