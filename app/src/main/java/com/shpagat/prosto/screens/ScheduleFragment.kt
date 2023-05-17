package com.shpagat.prosto.screens

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shpagat.prosto.adapter.TrainingsAdapter
import com.shpagat.prosto.databinding.FragmentScheduleBinding
import com.shpagat.prosto.model.TrainingModel
import com.shpagat.prosto.utils.APP
import com.shpagat.prosto.utils.gone
import com.shpagat.prosto.utils.visible
import com.shpagat.prosto.viewmodel.TrainingVM
import java.text.SimpleDateFormat
import java.util.*

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
        var cal = Calendar.getInstance()

        val dateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val date = SimpleDateFormat("dd.MM.yy").format(cal.time)
                val longDate = (SimpleDateFormat("dd.MM.yy").parse(date).time)
                    .toString()
                getTrainings(longDate)
            }

        DatePickerDialog(
            APP, dateSetListener,
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun getTrainings(longDate: String) {
        Log.e("", longDate)
        binding.title.text =
            SimpleDateFormat("dd.MM.yy").format(longDate.toLong()).toString()
        gone(binding.error)
        val currentList = mutableListOf<TrainingModel>()
        for (i in trainingVm.trainings) {
            if (i.date in longDate..longDate + 86400)
                currentList.add(i)
        }
        if (currentList.isNotEmpty()) setList(currentList)
        else visible(binding.error)
    }

    private fun initFields() {
        trainingVm = ViewModelProvider(APP)[TrainingVM::class.java]
        getTrainings((Date().time).toString())
    }

    private fun setList(list: List<TrainingModel>) {
        adapter = TrainingsAdapter()
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(APP)
        recyclerView.adapter = adapter
        adapter.setList(list)
    }
}