package com.shpagat.prosto.screens

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.shpagat.prosto.databinding.FragmentNoteBinding
import com.shpagat.prosto.model.NoteModel
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
            if (validateEmail(mail) && validatePhone(phone)) {
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
                appToast("Проверьте формат почты и телефона")
            }
        } else {
            appToast("Заполните поля")
        }
    }

    private fun noteUser(name: String, phone: String, mail: String) {
        if (existNote(phone)) {
            appToast("Вы уже записаны")
        } else {
            database.child(TRAININGS).child(noteVM.trainingDate).child(PLACES).get()
                .addOnSuccessListener {
                    val places = it.value.toString().toInt()
                    var ticketId: String
                    var oldRemained: Int
                    for (i in noteVM.usedTickets) {
                        if (i.phone == phone) {
                            oldRemained = i.remained.toInt()
                            if (oldRemained > 0) {
                                ticketId = i.id
                                database.child(USED_TICKETS).child(ticketId).child(REMAINED)
                                    .get().addOnSuccessListener { rem ->
                                        if (places > 0) {
                                            database.child(TRAININGS)
                                                .child(noteVM.trainingDate)
                                                .child(PLACES)
                                                .setValue(places - 1)
                                            database.child(USED_TICKETS).child(ticketId)
                                                .child(REMAINED).setValue(oldRemained - 1)
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
                                            addToAdminNotes(id, name, phone, mail)
                                            changeAdminTraining()
                                            changeTrainingPlaces()
                                            changeTicketRemained(phone)
                                            noteVM.tempTrainings.add(noteVM.trainingDate)
                                            appToast("Успех")
                                            noted = true
                                            binding.noteBtn.text = "Вы записаны"
                                        } else {
                                            appToast("Мест не осталось")
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

    private fun changeAdminTraining() {
        for (i in adminVM.trainings) {
            if (i.date == noteVM.trainingDate)
                i.places = (i.places.toInt() - 1).toString()
        }
    }

    private fun changeTicketRemained(phone: String) {
        for (i in noteVM.usedTickets) {
            if (i.phone == phone) {
                i.remained = (i.remained.toInt() - 1).toString()
            }
        }
    }

    private fun changeTrainingPlaces() {
        for (i in noteVM.trainings) {
            if (i.date == noteVM.trainingDate)
                i.places = (i.places.toInt() - 1).toString()
        }
    }

    private fun addToAdminNotes(id: String, name: String, phone: String, mail: String) {
        try {
            adminVM.notes.add(
                NoteModel(
                    id,
                    name,
                    phone,
                    mail,
                    noteVM.trainingTitle,
                    noteVM.trainingCoach,
                    noteVM.trainingDate,
                    noteVM.trainingPrice
                )
            )
        } catch (e: java.lang.Exception) {
            Log.e("", e.message.toString())
        }
    }

    private fun existNote(phone: String): Boolean {
        Log.e("", phone)
        var exist = false
        for (i in adminVM.notes) {
            val id = noteVM.trainingDate
            if (i.time == id && i.phone == phone && noteVM.tempTrainings.contains(id)
            ) {
                exist = true
                break
            } else {
                Log.e("", i.phone)
            }
        }
        return exist
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