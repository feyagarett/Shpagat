package com.shpagat.prosto.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shpagat.prosto.adapter.UsedTicketsAdapter
import com.shpagat.prosto.databinding.FragmentAdminTicketsBinding
import com.shpagat.prosto.utils.APP
import com.shpagat.prosto.viewmodel.AdminVM

class AdminTicketsFragment : Fragment() {
    private lateinit var binding: FragmentAdminTicketsBinding
    private lateinit var adminVM: AdminVM
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: UsedTicketsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdminTicketsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFields()
    }

    private fun initFields() {
        adminVM = ViewModelProvider(APP)[AdminVM::class.java]
        initList()
    }

    private fun initList() {
        adapter = UsedTicketsAdapter()
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(APP)
        recyclerView.adapter = adapter
        adapter.setList(adminVM.usedTickets)
    }
}