package com.shpagat.prosto

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.shpagat.prosto.databinding.ActivityMainBinding
import com.shpagat.prosto.utils.APP
import com.shpagat.prosto.utils.appToast

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
        APP = this
        initBottomNav()
    }

    private fun initFuns() {

    }

    private fun initBottomNav() {
        val navController = findNavController(R.id.main_frame)
        binding.bottomNav.setupWithNavController(navController)
    }
}