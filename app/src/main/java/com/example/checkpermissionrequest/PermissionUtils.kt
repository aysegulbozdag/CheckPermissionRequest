package com.example.checkpermissionrequest

import android.app.Activity
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts

object PermissionUtils {
    fun ComponentActivity.registerForActivityResult(permissionGranted: () -> Unit, permissionDenied: () -> Unit) =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                permissionGranted()
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_LONG).show()
            } else {
                permissionDenied()
                onPermissionRationaleShouldBeShown(
                    this
                )
                Toast.makeText(this, "Please grant permission", Toast.LENGTH_LONG).show()
            }
        }

    fun onPermissionRationaleShouldBeShown(context: Activity) {
        AlertDialog.Builder(context).setMessage("Please allow for permission")
            .setPositiveButton("Go to settings") { _, _ ->
                try {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", context.packageName, null)
                    intent.data = uri
                    context.startActivity(intent)

                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                }
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }.show()
    }
}