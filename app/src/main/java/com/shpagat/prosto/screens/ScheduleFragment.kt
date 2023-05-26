package com.shpagat.prosto.screens

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shpagat.prosto.R
import com.shpagat.prosto.adapter.TrainingsAdapter
import com.shpagat.prosto.databinding.FragmentScheduleBinding
import com.shpagat.prosto.model.TrainingModel
import com.shpagat.prosto.utils.APP
import com.shpagat.prosto.utils.gone
import com.shpagat.prosto.utils.visible
import com.shpagat.prosto.viewmodel.NoteVM
import java.text.SimpleDateFormat
import java.util.*

class ScheduleFragment : Fragment() {
    private lateinit var binding: FragmentScheduleBinding
    private lateinit var noteVm: NoteVM
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

    // инициализация DatePicker
    private fun getDate() {
        var cal = Calendar.getInstance()
        val dateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val date = SimpleDateFormat("dd.MM.yy").format(cal.time)
                val longDate = (SimpleDateFormat("dd.MM.yy").parse(date).time / 1000)
                    .toString()
                getTrainings(longDate)
            }

        val datePicker = DatePickerDialog(
            APP, dateSetListener,
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        )
        datePicker.datePicker.minDate = Date().time
        datePicker.window?.setBackgroundDrawable(APP.getDrawable(R.drawable.date_picker))
        datePicker.show()
    }

    // получение списка тренировок, основываясь на выбранной дате
    private fun getTrainings(longDate: String) {
        val date = SimpleDateFormat("dd.MM.yy").format(Date().time).toString()
        val currentLong = (SimpleDateFormat("dd.MM.yy").parse(date).time / 1000)
            .toString()
        binding.title.text =
            SimpleDateFormat("dd.MM.yy").format(longDate.toLong() * 1000).toString()
        val currentList = mutableListOf<TrainingModel>()
        for (i in noteVm.trainings) {
            if (i.date.toLong() in longDate.toLong()..longDate.toLong() + 86400) {
                if (longDate.toLong() > currentLong.toLong() - 1000)
                    currentList.add(i)
            }
        }
        if (currentList.isNotEmpty()) {
            gone(binding.error)
            visible(binding.recyclerView)
            setList(currentList)
        } else {
            gone(binding.recyclerView)
            visible(binding.error)
        }
    }

    // инициализация вьюмодели, получение начальной даты
    private fun initFields() {
        noteVm = ViewModelProvider(APP)[NoteVM::class.java]
        val date = SimpleDateFormat("dd.MM.yy").format(Date().time).toString()
        val longDate = (SimpleDateFormat("dd.MM.yy").parse(date).time / 1000)
            .toString()
        getTrainings(longDate)
    }

    // отображение списка тренировок
    private fun setList(list: List<TrainingModel>) {
        adapter = TrainingsAdapter()
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(APP)
        recyclerView.adapter = adapter
        adapter.setList(list)
    }
}