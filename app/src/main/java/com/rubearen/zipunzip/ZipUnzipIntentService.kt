package com.rubearen.zipunzip

import android.app.IntentService
import android.content.Intent
import android.content.Context

import android.util.Log
import androidx.core.app.JobIntentService
import com.rubearen.zipunzip.utils.FileHelper


private const val ACTION_ZIP = "ZIP"
private const val ACTION_UNZIP = "UNZIP"

private const val SOURCE_PATH = "sourcePath"
private const val DESTINATION_PATH = "destinationPath"

/**
 * An [IntentService] subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * methods.
 */

class ZipUnzipIntentService : JobIntentService() {

    override fun onHandleWork(intent: Intent) {
        when (intent.action) {
            ACTION_ZIP -> {
                val param1 = intent.getStringExtra(SOURCE_PATH)
                val param2 = intent.getStringExtra(DESTINATION_PATH)
                handleActionZip(param1!!, param2!!)
            }
            ACTION_UNZIP -> {
                val param1 = intent.getStringExtra(SOURCE_PATH)
                val param2 = intent.getStringExtra(DESTINATION_PATH)
                handleActionUnzip(param1!!, param2!!)
            }
        }
    }

    override fun onDestroy() {
        Log.i("UnzipService", "service destroyed")
    }

    /**
     * Handle action ZIP in the provided background thread with the provided
     * parameters.
     */
    private fun handleActionZip(sourcePath: String, destinationPath: String) {
        FileHelper.zip(sourcePath, destinationPath, "zip", false)
    }

    /**
     * Handle action UNZIP in the provided background thread with the provided
     * parameters.
     */
    private fun handleActionUnzip(sourcePath: String, destinationPath: String) {
        FileHelper.unzip(sourcePath, destinationPath)
    }

    companion object {
        /**
         * Starts this service to perform action ZIP with the given parameters. If
         * the service is already performing a task this action will be queued.
         */

        @JvmStatic
        fun startActionZip(context: Context, sourcePath: String, destinationPath: String) {
            val intent = Intent(context, ZipUnzipIntentService::class.java).apply {
                action = ACTION_ZIP
                putExtra(SOURCE_PATH, sourcePath)
                putExtra(DESTINATION_PATH, destinationPath)
            }
            context.startService(intent)
        }

        /**
         * Starts this service to perform action UNZIP with the given parameters. If
         * the service is already performing a task this action will be queued.
         */

        @JvmStatic
        fun startActionUnzip(context: Context, sourcePath: String, destinationPath: String) {
            val intent = Intent(context, ZipUnzipIntentService::class.java).apply {
                action = ACTION_UNZIP
                putExtra(SOURCE_PATH, sourcePath)
                putExtra(DESTINATION_PATH, destinationPath)
            }
            context.startService(intent)
        }
    }
}