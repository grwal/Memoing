package com.grwal.memoing

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.grwal.memoing.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding = ActivityMainBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}