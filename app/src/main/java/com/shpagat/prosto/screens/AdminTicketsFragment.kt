package com.shpagat.prosto.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shpagat.prosto.R
import com.shpagat.prosto.adapter.UsedTicketsAdapter
import com.shpagat.prosto.databinding.FragmentAdminTicketsBinding
import com.shpagat.prosto.model.UsedTicketModel
import com.shpagat.prosto.utils.*
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
        initFuns()
    }

    private fun initFuns() {
        binding.addBtn.setOnClickListener {
            val navController = Navigation.findNavController(APP, R.id.main_frame)
            navController.navigate(R.id.action_adminTickets_to_adminAddTicket)
        }
        initSwipeListener()
    }

    private fun initFields() {
        adminVM = ViewModelProvider(APP)[AdminVM::class.java]
        adminVM.editTicket = false
        initList()
    }

    private fun initList() {
        adapter = UsedTicketsAdapter()
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(APP)
        recyclerView.adapter = adapter
        adapter.setList(adminVM.usedTickets)
    }

    private fun initSwipeListener() {
        val swipeListener = object : AppSwipeListener(APP) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                when (direction) {
                    ItemTouchHelper.RIGHT -> {
                        tryDelete(viewHolder)
                    }
                }
            }
        }
        val touchHelper = ItemTouchHelper(swipeListener)
        touchHelper.attachToRecyclerView(recyclerView)
    }

    private fun tryDelete(viewHolder: RecyclerView.ViewHolder) {
        deleteTicket(adapter.getItem(viewHolder.absoluteAdapterPosition), viewHolder)
    }

    private fun deleteTicket(ticket: UsedTicketModel, viewHolder: RecyclerView.ViewHolder) {
        visible(binding.deleteBtn)
        visible(binding.cancelBtn)
        gone(binding.addBtn)
        binding.deleteBtn.setOnClickListener {
            database.child(USED_TICKETS).child(ticket.id).removeValue()
            adminVM.usedTickets.remove(ticket)
            adapter.deleteItem(viewHolder.absoluteAdapterPosition)
            gone(binding.deleteBtn)
            gone(binding.cancelBtn)
            visible(binding.addBtn)
        }
        binding.cancelBtn.setOnClickListener {
            adapter.addItem(viewHolder.absoluteAdapterPosition, ticket)
            adapter.deleteItem(viewHolder.absoluteAdapterPosition)
            gone(binding.deleteBtn)
            gone(binding.cancelBtn)
            visible(binding.addBtn)
        }
    }
}