package com.szirakiapps.audiorecorderplugin

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import com.szirakiapps.audiorecorderplugin.di.ContextComponent
import com.szirakiapps.audiorecorderplugin.model.util.RuntimeLocaleUtil
import com.szirakiapps.audiorecorderplugin.view.RecorderFragment
import com.szirakiapps.audiorecorderplugin.view.dialog.PermissionNotAcceptedDialog
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var recorderFragment: RecorderFragment

    override fun onCreate(savedInstanceState: Bundle?) {

        if (intent.getBooleanExtra("dark_mode", false)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        val lang = intent.getStringExtra("lang") ?: ""
        RuntimeLocaleUtil.setLocale(this, lang)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ContextComponent.build(this).inject(this)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame, recorderFragment, "recorder")
            .commit()

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.RECORD_AUDIO),
                AUDIO_PERMISSION_REQUEST
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when {
            requestCode == AUDIO_PERMISSION_REQUEST && !grantResults.contains(PackageManager.PERMISSION_GRANTED) -> {
                PermissionNotAcceptedDialog.show(this)
            }
        }
    }

    companion object {
        const val AUDIO_PERMISSION_REQUEST = 1467
    }
}
