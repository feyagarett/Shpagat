package com.shpagat.prosto.viewmodel

import androidx.lifecycle.ViewModel
import com.shpagat.prosto.model.DirectionModel

// viewmodel направлений
class DirectionVM : ViewModel() {
    var directions = mutableListOf<DirectionModel>()
    var title = ""
    var desc = ""
}