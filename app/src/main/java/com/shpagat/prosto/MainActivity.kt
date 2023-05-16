package com.shpagat.prosto

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.database.FirebaseDatabase
import com.shpagat.prosto.databinding.ActivityMainBinding
import com.shpagat.prosto.services.GetNotesService
import com.shpagat.prosto.services.GetUsedTicketsService
import com.shpagat.prosto.utils.APP
import com.shpagat.prosto.utils.database

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
        database = FirebaseDatabase.getInstance().reference
        initBottomNav()
        startService(Intent(APP, GetUsedTicketsService::class.java))
        startService(Intent(APP, GetNotesService::class.java))
    }

    private fun initFuns() {

    }

    private fun initBottomNav() {
        val navController = findNavController(R.id.main_frame)
        binding.bottomNav.setupWithNavController(navController)
    }
}