package com.shpagat.prosto.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shpagat.prosto.adapter.UsedTicketsAdapter
import com.shpagat.prosto.databinding.FragmentAdminStatisticsBinding
import com.shpagat.prosto.viewmodel.AdminVM

class AdminStatisticsFragment : Fragment() {
    private lateinit var binding: FragmentAdminStatisticsBinding
    private lateinit var adminVM: AdminVM
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: UsedTicketsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdminStatisticsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFields()
    }

    private fun initFields() {
        setStatistics()
    }

    private fun setStatistics() {

    }

}