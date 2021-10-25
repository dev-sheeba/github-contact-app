package com.hfad.contactapps.permission

import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class ContactPermissionFacade(val activity: AppCompatActivity): ContactPermissionController {
    private lateinit var activityResultLauncher:ActivityResultLauncher<String>
    private lateinit var callback: (isGranted: Boolean) -> Unit

    override fun register(callback: (isGranted: Boolean) -> Unit) {
        this.callback = callback

        activityResultLauncher = activity.registerForActivityResult(
            ActivityResultContracts.RequestPermission(),
            callback
        )
    }

    override fun request() {
        val permission = android.Manifest.permission.READ_CONTACTS
        when {
            ContextCompat.checkSelfPermission(
                activity,
                permission
            ) == PackageManager.PERMISSION_GRANTED -> {
                callback(true)
            }
            ActivityCompat.shouldShowRequestPermissionRationale(
                activity,
                permission
            ) -> {
                activityResultLauncher.launch(
                    permission
                )
            }
            else -> {
                activityResultLauncher.launch(
                    permission
                )
            }
        }
    }
}