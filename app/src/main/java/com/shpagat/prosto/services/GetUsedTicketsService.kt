package com.shpagat.prosto.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.lifecycle.ViewModelProvider
import com.shpagat.prosto.model.UsedTicketModel
import com.shpagat.prosto.utils.*
import com.shpagat.prosto.viewmodel.AdminVM
import com.shpagat.prosto.viewmodel.NoteVM

// фоновый сервис для получения используемых абонементов
class GetUsedTicketsService : Service() {

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val usedTicketsDb = database.child(USED_TICKETS)
        usedTicketsDb.addListenerForSingleValueEvent(AppValueEventListener {
            if (it.exists()) {
                val adminVM = ViewModelProvider(APP)[AdminVM::class.java]
                val noteVM = ViewModelProvider(APP)[NoteVM::class.java]
                for (s in it.children) {
                    val title = s.child(TITLE).value.toString()
                    val remained = s.child(REMAINED).value.toString()
                    val name = s.child(NAME).value.toString()
                    val phone = MyCrypt.decrypt(s.child(PHONE).value.toString()).toString()
                    val mail = MyCrypt.decrypt(s.child(MAIL).value.toString()).toString()
                    val id = s.key.toString()
                    adminVM.usedTickets.add(UsedTicketModel(title, remained, name, phone, mail, id))
                    noteVM.usedTickets.add(UsedTicketModel(title, remained, name, phone, mail, id))
                }
            }
        })

        return super.onStartCommand(intent, flags, startId)
    }
}