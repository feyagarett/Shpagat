package com.shpagat.prosto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.shpagat.prosto.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initFields()
        initFuns()
    }

    private fun initFields() {

    }

    private fun initFuns() {

    }
}