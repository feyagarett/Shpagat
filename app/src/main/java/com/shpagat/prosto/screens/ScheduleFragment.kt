package com.shpagat.prosto.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shpagat.prosto.adapter.TrainingsAdapter
import com.shpagat.prosto.databinding.FragmentScheduleBinding
import com.shpagat.prosto.utils.APP
import com.shpagat.prosto.viewmodel.TrainingVM

class ScheduleFragment : Fragment() {
    private lateinit var binding: FragmentScheduleBinding
    private lateinit var trainingVm: TrainingVM
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TrainingsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScheduleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFields()
        initFuns()
    }

    private fun initFuns() {
        binding.date.setOnClickListener {
            getDate()
        }
    }

    private fun getDate() {

    }

    private fun initFields() {
        trainingVm = ViewModelProvider(APP)[TrainingVM::class.java]
        initList()
    }

    private fun initList() {
        adapter = TrainingsAdapter()
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(APP)
        recyclerView.adapter = adapter
        adapter.setList(trainingVm.trainings)
    }
}