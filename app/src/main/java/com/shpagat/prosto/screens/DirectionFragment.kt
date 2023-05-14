package com.shpagat.prosto.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.shpagat.prosto.R
import com.shpagat.prosto.databinding.FragmentDirectionBinding
import com.shpagat.prosto.databinding.FragmentScheduleBinding
import com.shpagat.prosto.utils.APP
import com.shpagat.prosto.viewmodel.DirectionVM

class DirectionFragment : Fragment() {
    private lateinit var binding: FragmentDirectionBinding
    private lateinit var directionVM: DirectionVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDirectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        directionVM = ViewModelProvider(APP)[DirectionVM::class.java]
        setData()
    }

    private fun setData() {
        binding.title.text = directionVM.title
        binding.desc.text = directionVM.desc
    }
}