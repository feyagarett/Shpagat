package com.shpagat.prosto.viewmodel

import androidx.lifecycle.ViewModel
import com.shpagat.prosto.model.DirectionModel
import com.shpagat.prosto.model.UsedTicketModel

class AdminVM : ViewModel() {
    var usedTickets = mutableListOf<UsedTicketModel>()
}