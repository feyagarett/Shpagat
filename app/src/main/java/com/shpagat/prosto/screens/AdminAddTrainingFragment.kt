package com.shpagat.prosto.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.shpagat.prosto.databinding.FragmentAdminAddTrainingBinding
import com.shpagat.prosto.utils.*
import java.text.SimpleDateFormat

class AdminAddTrainingFragment : Fragment() {
    private lateinit var binding: FragmentAdminAddTrainingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdminAddTrainingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFuns()
    }

    private fun initFuns() {
        binding.addBtn.setOnClickListener {
            tryAddTraining()
        }
    }

    private fun tryAddTraining() {
        val title = myGetText(binding.title)
        val coach = myGetText(binding.coach)
        val price = myGetText(binding.price)
        val places = myGetText(binding.places)
        val date = myGetText(binding.date)
        if (title.isNotEmpty() && coach.isNotEmpty() && price.isNotEmpty()
            && places.isNotEmpty() && date.isNotEmpty()
        ) {
            try {
                val longDate = SimpleDateFormat("dd.MM.yy HH:mm").parse(date).time
                val trainingDb = database.child(TRAININGS).child(longDate.toString())
                trainingDb.child(TITLE).setValue(title)
                trainingDb.child(COACH).setValue(coach)
                trainingDb.child(PRICE).setValue(price)
                trainingDb.child(PLACES).setValue(places)
                appToast("Успех")
                clearInputs()
            } catch (e: Exception) {
                appToast("Неверный формат даты и времени")
            }
        } else {
            appToast("Заполните поля")
        }
    }

    private fun clearInputs() {
        binding.title.text.clear()
        binding.coach.text.clear()
        binding.price.text.clear()
        binding.date.text.clear()
        binding.places.text.clear()
    }
}