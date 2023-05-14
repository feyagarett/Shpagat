package com.shpagat.prosto.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shpagat.prosto.adapter.TicketsAdapter
import com.shpagat.prosto.databinding.FragmentTicketsBinding
import com.shpagat.prosto.model.TicketModel
import com.shpagat.prosto.utils.APP

class TicketsFragment : Fragment() {
    private lateinit var binding: FragmentTicketsBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TicketsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTicketsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        getTickets()
    }

    private fun getTickets() {
        val ticketsList = mutableListOf<TicketModel>()
        ticketsList.add(
            TicketModel(
                "1 занятие",
                "600 ₽", "510 ₽"
            )
        )
        ticketsList.add(
            TicketModel(
                "4 занятия",
                "2200 ₽", "1870 ₽"
            )
        )
        ticketsList.add(
            TicketModel(
                "8 занятий",
                "4000 ₽", "3400 ₽"
            )
        )
        ticketsList.add(
            TicketModel(
                "12 занятий",
                "5400 ₽", "4590 ₽"
            )
        )
        initList(ticketsList)
    }

    private fun initList(ticketsList: MutableList<TicketModel>) {
        adapter = TicketsAdapter()
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(APP)
        recyclerView.adapter = adapter
        adapter.setList(ticketsList)
    }
}