package com.shpagat.prosto.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shpagat.prosto.adapter.NotesAdapter
import com.shpagat.prosto.databinding.FragmentAdminNotesBinding
import com.shpagat.prosto.model.NoteModel
import com.shpagat.prosto.utils.*
import com.shpagat.prosto.viewmodel.AdminVM

class AdminNotesFragment : Fragment() {
    private lateinit var binding: FragmentAdminNotesBinding
    private lateinit var adminVM: AdminVM
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NotesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdminNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFields()
    }

    private fun initFields() {
        adminVM = ViewModelProvider(APP)[AdminVM::class.java]
        initList()
        initSwipeListener()
    }

    private fun initList() {
        adapter = NotesAdapter()
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(APP)
        recyclerView.adapter = adapter
        adapter.setList(adminVM.notes)
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
        deleteNote(adapter.getItem(viewHolder.absoluteAdapterPosition), viewHolder)
    }

    private fun deleteNote(note: NoteModel, viewHolder: RecyclerView.ViewHolder) {
        visible(binding.deleteBtn)
        visible(binding.cancelBtn)
        binding.deleteBtn.setOnClickListener {
            database.child(NOTES).child(note.id).removeValue()
            adminVM.notes.remove(note)
            adapter.deleteItem(viewHolder.absoluteAdapterPosition)
            gone(binding.deleteBtn)
            gone(binding.cancelBtn)
        }
        binding.cancelBtn.setOnClickListener {
            adapter.addItem(viewHolder.absoluteAdapterPosition, note)
            adapter.deleteItem(viewHolder.absoluteAdapterPosition)
            gone(binding.deleteBtn)
            gone(binding.cancelBtn)
        }
    }

}