package com.shpagat.prosto.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.shpagat.prosto.R
import com.shpagat.prosto.adapter.TrainingsAdapter
import com.shpagat.prosto.databinding.FragmentAdminScheduleBinding
import com.shpagat.prosto.utils.APP

class AdminScheduleFragment : Fragment() {
    private lateinit var binding: FragmentAdminScheduleBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TrainingsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdminScheduleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        initFuns()
    }

    private fun initFuns() {
        binding.addBtn.setOnClickListener {
            val navController = Navigation.findNavController(APP, R.id.main_frame)
            navController.navigate(R.id.action_adminSchedule_to_adminAddTraining)
        }
    }

    private fun initList() {

    }
}