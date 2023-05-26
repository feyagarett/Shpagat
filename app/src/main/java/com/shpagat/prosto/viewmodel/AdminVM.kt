package com.shpagat.prosto.viewmodel

import androidx.lifecycle.ViewModel
import com.shpagat.prosto.model.NoteModel
import com.shpagat.prosto.model.StatisticsModel
import com.shpagat.prosto.model.TrainingModel
import com.shpagat.prosto.model.UsedTicketModel

//viewmodel для администратора
class AdminVM : ViewModel() {
    var usedTickets = mutableListOf<UsedTicketModel>()
    var notes = mutableListOf<NoteModel>()
    var trainings = mutableListOf<TrainingModel>()
    var statistics = mutableListOf<StatisticsModel>()

    var editTraining = false
    var trainingDate = ""
    var trainingCoach = ""
    var trainingPlaces = ""
    var trainingPrice = ""
    var trainingTitle = ""

    var editTicket = false
    var ticketTitle = ""
    var ticketRemained = ""
    var ticketName = ""
    var ticketPhone = ""
    var ticketMail = ""
    var ticketId = ""
}