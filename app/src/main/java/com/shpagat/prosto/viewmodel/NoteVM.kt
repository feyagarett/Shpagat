package com.shpagat.prosto.viewmodel

import androidx.lifecycle.ViewModel
import com.shpagat.prosto.model.TrainingModel
import com.shpagat.prosto.model.UsedTicketModel

class NoteVM : ViewModel() {
    var trainings = mutableListOf<TrainingModel>()
    var usedTickets = mutableListOf<UsedTicketModel>()

    var trainingId = ""
    var trainingCoach = ""
    var trainingDate = ""
    var trainingPrice = ""
    var trainingTitle = ""
}