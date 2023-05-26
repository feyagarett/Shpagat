package com.shpagat.prosto.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.lifecycle.ViewModelProvider
import com.shpagat.prosto.model.NoteModel
import com.shpagat.prosto.utils.*
import com.shpagat.prosto.viewmodel.AdminVM

// фоновый сервис для получения записей клиентов
class GetNotesService : Service() {

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val usedTicketsDb = database.child(NOTES)
        usedTicketsDb.addListenerForSingleValueEvent(AppValueEventListener {
            if (it.exists()) {
                val adminVM = ViewModelProvider(APP)[AdminVM::class.java]
                for (s in it.children) {
                    val id = s.key.toString()
                    val name = s.child(NAME).value.toString()
                    val phone = MyCrypt.decrypt(s.child(PHONE).value.toString()).toString()
                    val mail = MyCrypt.decrypt(s.child(MAIL).value.toString()).toString()
                    val title = s.child(TITLE).value.toString()
                    val coach = s.child(COACH).value.toString()
                    val price = s.child(PRICE).value.toString()
                    val time = s.child(TIME).value.toString()
                    adminVM.notes.add(NoteModel(id, name, phone, mail, title, coach, time, price))
                }
                adminVM.notes = adminVM.notes.sortedBy { it.time } as MutableList<NoteModel>
            }
        })

        return super.onStartCommand(intent, flags, startId)
    }
}