package com.shpagat.prosto.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.shpagat.prosto.R
import com.shpagat.prosto.databinding.FragmentProfileBinding
import com.shpagat.prosto.utils.APP

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFuns()
    }

    // инициализация кнопок
    private fun initFuns() {
        binding.statistics.setOnClickListener {
            val navController = Navigation.findNavController(APP, R.id.main_frame)
            navController.navigate(R.id.action_profile_to_adminStatistics)
        }
        binding.schedule.setOnClickListener {
            val navController = Navigation.findNavController(APP, R.id.main_frame)
            navController.navigate(R.id.action_profile_to_adminSchedule)
        }
        binding.notes.setOnClickListener {
            val navController = Navigation.findNavController(APP, R.id.main_frame)
            navController.navigate(R.id.action_profile_to_adminNotes)
        }
        binding.tickets.setOnClickListener {
            val navController = Navigation.findNavController(APP, R.id.main_frame)
            navController.navigate(R.id.action_profile_to_adminTickets)
        }
    }
}