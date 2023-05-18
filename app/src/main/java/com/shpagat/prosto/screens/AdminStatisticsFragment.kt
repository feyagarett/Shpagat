package com.shpagat.prosto.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shpagat.prosto.adapter.StatisticsAdapter
import com.shpagat.prosto.databinding.FragmentAdminStatisticsBinding
import com.shpagat.prosto.model.StatisticsModel
import com.shpagat.prosto.utils.*
import com.shpagat.prosto.viewmodel.AdminVM

class AdminStatisticsFragment : Fragment() {
    private lateinit var binding: FragmentAdminStatisticsBinding
    private lateinit var adminVM: AdminVM
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: StatisticsAdapter

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
        adminVM = ViewModelProvider(APP)[AdminVM::class.java]
        setStatistics()
    }

    private fun setStatistics() {
        adminVM.statistics.clear()
        binding.amount.text = "Общее число посещений: ${adminVM.notes.size}"
        getOneDirection(SCHOOL)
        getOneDirection(KUNDALINI)
        getOneDirection(BACK)
        getOneDirection(AERO)
        getOneDirection(FULL)
        getOneDirection(JOGA)
        getOneDirection(MFR)
        getOneDirection(SIMETRICA)
        getOneDirection(PIN)
        setList()
    }

    private fun setList() {
        adapter = StatisticsAdapter()
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(APP)
        recyclerView.adapter = adapter
        adapter.setList(adminVM.statistics)
    }

    private fun getOneDirection(direction: String) {
        var size = 0
        for (i in adminVM.notes) {
            if (i.title == direction) {
                size++
            }
        }
        if (size > 0) {
            val percent = size.toFloat() * 100 / adminVM.notes.size.toFloat()
            adminVM.statistics.add(
                StatisticsModel(
                    direction,
                    size.toString(),
                    percent.toInt().toString()
                )
            )
        }
    }

}