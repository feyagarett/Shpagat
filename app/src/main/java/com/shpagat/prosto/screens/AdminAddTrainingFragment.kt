package com.shpagat.prosto.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shpagat.prosto.R
import com.shpagat.prosto.databinding.FragmentAdminAddTrainingBinding
import com.shpagat.prosto.databinding.FragmentAdminScheduleBinding

class AdminAddTrainingFragment : Fragment() {
    private lateinit var binding: FragmentAdminAddTrainingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdminAddTrainingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}