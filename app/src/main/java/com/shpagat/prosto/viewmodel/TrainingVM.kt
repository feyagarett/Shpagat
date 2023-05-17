package com.shpagat.prosto.viewmodel

import androidx.lifecycle.ViewModel
import com.shpagat.prosto.model.TrainingModel

class TrainingVM: ViewModel() {
    var trainings = mutableListOf<TrainingModel>()
}