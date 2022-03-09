package com.scope.application.utils

import android.Manifest
import android.content.Intent
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.navigation.findNavController
import com.scope.application.R
import com.scope.application.databinding.ActivityMainBinding

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()

        Handler(Looper.getMainLooper())
            .postDelayed({
                openMainActivity()
            }
                ,3000)

    }

    private fun openMainActivity() {
        startActivity(Intent(this, BaseActivity::class.java))
        finish()
    }


}