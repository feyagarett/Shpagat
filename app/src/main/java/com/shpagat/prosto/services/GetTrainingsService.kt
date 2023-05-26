package com.shpagat.prosto.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.lifecycle.ViewModelProvider
import com.shpagat.prosto.model.TrainingModel
import com.shpagat.prosto.utils.*
import com.shpagat.prosto.viewmodel.AdminVM
import com.shpagat.prosto.viewmodel.NoteVM

// фоновый сервис для получения текущих тренировок
class GetTrainingsService : Service() {

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val usedTicketsDb = database.child(TRAININGS)
        usedTicketsDb.addListenerForSingleValueEvent(AppValueEventListener {
            if (it.exists()) {
                val adminVM = ViewModelProvider(APP)[AdminVM::class.java]
                val noteVM = ViewModelProvider(APP)[NoteVM::class.java]
                adminVM.trainings.clear()
                noteVM.trainings.clear()
                for (s in it.children) {
                    val date = s.key.toString()
                    val title = s.child(TITLE).value.toString()
                    val coach = s.child(COACH).value.toString()
                    val price = s.child(PRICE).value.toString()
                    val places = s.child(PLACES).value.toString()
                    adminVM.trainings.add(TrainingModel(title, coach, date, price, places))
                    noteVM.trainings.add(TrainingModel(title, coach, date, price, places))
                }
            }
        })

        return super.onStartCommand(intent, flags, startId)
    }
}