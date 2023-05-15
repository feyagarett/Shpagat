package com.shpagat.prosto.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shpagat.prosto.R
import com.shpagat.prosto.databinding.FragmentAdminBinding
import com.shpagat.prosto.databinding.FragmentAdminNotesBinding

class AdminNotesFragment : Fragment() {
    private lateinit var binding: FragmentAdminNotesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdminNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}