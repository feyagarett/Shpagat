package com.shpagat.prosto.screens

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.shpagat.prosto.databinding.FragmentNoteBinding
import com.shpagat.prosto.utils.*
import com.shpagat.prosto.viewmodel.AdminVM
import com.shpagat.prosto.viewmodel.NoteVM
import java.text.SimpleDateFormat
import java.util.*

class NoteFragment : Fragment() {
    private lateinit var binding: FragmentNoteBinding
    private lateinit var noteVM: NoteVM
    private lateinit var adminVM: AdminVM
    private var noted = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFields()
        initFuns()
    }

    private fun initFuns() {
        binding.noteBtn.setOnClickListener {
            if (!noted) tryNote()
        }
    }

    private fun tryNote() {
        var existTicket = false
        val name = myGetText(binding.name)
        val phone = myGetText(binding.phone)
        val mail = myGetText(binding.email)
        if (name.isNotEmpty() && phone.isNotEmpty() && mail.isNotEmpty()) {
            for (i in noteVM.usedTickets) {
                if (i.phone == phone) {
                    existTicket = true
                    break
                }
            }
            if (existTicket) {
                noteUser(name, phone, mail)
            } else {
                appToast("У вас нет абонемента")
            }
        } else {
            appToast("Заполните поля")
        }
    }

    private fun noteUser(name: String, phone: String, mail: String) {
        var exist = false
        for (i in adminVM.notes) {
            if (i.time == noteVM.trainingDate && i.phone == phone) {
                exist = true
                break
            }
        }
        if (exist) appToast("Вы уже записаны") else {
            database.child(TRAININGS).child(noteVM.trainingDate).child(PLACES).get()
                .addOnSuccessListener {
                    if (it.exists()) {
                        val places = it.value.toString().toInt()
                        var ticketId = ""
                        var oldRemained = 0
                        for (i in noteVM.usedTickets) {
                            if (i.phone == phone) {
                                oldRemained = i.remained.toInt()
                                if (oldRemained > 0) {
                                    ticketId = i.id
                                    database.child(USED_TICKETS).child(ticketId).child(REMAINED)
                                        .get().addOnSuccessListener { rem ->
                                            if (rem.exists()) {
                                                val remained = rem.value.toString().toInt()
                                                if (remained > 0) {
                                                    database.child(USED_TICKETS).child(ticketId)
                                                        .child(REMAINED).setValue(remained - 1)
                                                    if (places > 0)
                                                        database.child(TRAININGS)
                                                            .child(noteVM.trainingDate)
                                                            .child(PLACES)
                                                            .setValue(places - 1)
                                                    else appToast("Мест не осталось")
                                                    val id = UUID.randomUUID().toString()
                                                    val notesDb = database.child(NOTES).child(id)
                                                    notesDb.child(COACH)
                                                        .setValue(noteVM.trainingCoach)
                                                    notesDb.child(MAIL)
                                                        .setValue(MyCrypt.encrypt(mail))
                                                    notesDb.child(NAME).setValue(name)
                                                    notesDb.child(PHONE)
                                                        .setValue(MyCrypt.encrypt(phone))
                                                    notesDb.child(PRICE)
                                                        .setValue(noteVM.trainingPrice)
                                                    notesDb.child(TIME)
                                                        .setValue(noteVM.trainingDate)
                                                    notesDb.child(TITLE)
                                                        .setValue(noteVM.trainingTitle)
                                                    appToast("Успех")
                                                    noted = true
                                                    binding.noteBtn.text = "Вы записаны"
                                                } else {
                                                    appToast("В абонементе не осталось занятий")
                                                }
                                            } else {
                                                appToast("Что-то пошло не так..")
                                            }
                                        }.addOnFailureListener { e ->
                                            Log.e("", e.message.toString())
                                        }
                                } else {
                                    appToast("В абонементе не осталось занятий")
                                }
                            }
                        }
                    }
                }
        }
    }

    private fun initFields() {
        noteVM = ViewModelProvider(APP)[NoteVM::class.java]
        adminVM = ViewModelProvider(APP)[AdminVM::class.java]
        setData()
    }

    private fun setData() {
        binding.title.text = noteVM.trainingTitle
        binding.time.text =
            SimpleDateFormat("dd.MM.yy HH:mm").format(noteVM.trainingDate.toLong() * 1000)
                .toString()
        binding.price.text = "${noteVM.trainingPrice} ₽"
        binding.coach.text = "Тренер: ${noteVM.trainingCoach}"
    }
}