package com.shpagat.prosto.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.shpagat.prosto.databinding.FragmentAdminAddTrainingBinding
import com.shpagat.prosto.model.TrainingModel
import com.shpagat.prosto.utils.*
import com.shpagat.prosto.viewmodel.AdminVM
import java.text.SimpleDateFormat

class AdminAddTrainingFragment : Fragment() {
    private lateinit var binding: FragmentAdminAddTrainingBinding
    private lateinit var adminVM: AdminVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdminAddTrainingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFields()
        initFuns()
    }

    private fun initFields() {
        adminVM = ViewModelProvider(APP)[AdminVM::class.java]
        if (adminVM.editTraining) setData()
    }

    private fun setData() {
        binding.title.setText(adminVM.trainingTitle)
        binding.date.setText(
            SimpleDateFormat("dd.MM.yy HH:mm").format(adminVM.trainingDate.toLong() * 1000)
                .toString()
        )
        binding.price.setText(adminVM.trainingPrice)
        binding.coach.setText(adminVM.trainingCoach)
        binding.places.setText(adminVM.trainingPlaces)
    }

    private fun initFuns() {
        if (adminVM.editTraining) {
            binding.addBtn.text = "Сохранить"
            binding.addBtn.setOnClickListener {
                trySaveTraining()
            }
        } else {
            binding.addBtn.text = "Добавить"
            binding.addBtn.setOnClickListener {
                tryAddTraining()
            }
        }
    }

    private fun trySaveTraining() {
        val title = myGetText(binding.title)
        val coach = myGetText(binding.coach)
        val price = myGetText(binding.price)
        val places = myGetText(binding.places)
        val date = myGetText(binding.date)
        if (title.isNotEmpty() && coach.isNotEmpty() && price.isNotEmpty() && places.isNotEmpty() && date.isNotEmpty()) {
            try {
                adminVM.editTraining = false
                val longDate = SimpleDateFormat("dd.MM.yy HH:mm").parse(date).time / 1000
                val trainingDb = database.child(TRAININGS).child(longDate.toString())
                trainingDb.child(TITLE).setValue(title)
                trainingDb.child(COACH).setValue(coach)
                trainingDb.child(PRICE).setValue(price)
                trainingDb.child(PLACES).setValue(places)
                for (i in adminVM.trainings) {
                    if (i.date.toLong() == adminVM.trainingDate.toLong())
                        adminVM.trainings.remove(i)
                }
                adminVM.trainings.add(
                    TrainingModel(
                        title, coach, longDate.toString(), price, places
                    )
                )
                if (longDate != adminVM.trainingDate.toLong())
                    database.child(TRAININGS).child(adminVM.trainingDate).removeValue()

                appToast("Успех")
                clearInputs()
            } catch (e: Exception) {
                appToast("Подтвердите изменение")
            }
        } else {
            appToast("Заполните поля")
        }
    }

    private fun tryAddTraining() {
        val title = myGetText(binding.title)
        val coach = myGetText(binding.coach)
        val price = myGetText(binding.price)
        val places = myGetText(binding.places)
        val date = myGetText(binding.date)
        if (title.isNotEmpty() && coach.isNotEmpty() && price.isNotEmpty() && places.isNotEmpty() && date.isNotEmpty()) {
            try {
                val longDate = SimpleDateFormat("dd.MM.yy HH:mm").parse(date).time / 1000
                val trainingDb = database.child(TRAININGS).child(longDate.toString())
                trainingDb.child(TITLE).setValue(title)
                trainingDb.child(COACH).setValue(coach)
                trainingDb.child(PRICE).setValue(price)
                trainingDb.child(PLACES).setValue(places)
                adminVM.trainings.add(
                    TrainingModel(
                        title,
                        coach,
                        longDate.toString(),
                        price,
                        places
                    )
                )
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