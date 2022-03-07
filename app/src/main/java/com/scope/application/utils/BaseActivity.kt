package com.scope.application.utils

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
        super.onBackPressed()

        binding.navHostFragment.findNavController()?.let { it.popBackStack() }

    }

}