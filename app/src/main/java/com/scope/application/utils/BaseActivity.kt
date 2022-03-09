package com.scope.application.utils

import android.Manifest
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.navigation.findNavController
import com.scope.application.databinding.ActivityMainBinding

class BaseActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }

    override fun onBackPressed() {
        binding.navHostFragment.findNavController()?.let { it.popBackStack() }

    }


}