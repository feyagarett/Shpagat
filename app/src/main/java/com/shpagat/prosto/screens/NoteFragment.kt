package com.shpagat.prosto.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.shpagat.prosto.databinding.FragmentNoteBinding
import com.shpagat.prosto.utils.*
import com.shpagat.prosto.viewmodel.NoteVM
import java.text.SimpleDateFormat
import java.util.*

class NoteFragment : Fragment() {
    private lateinit var binding: FragmentNoteBinding
    private lateinit var noteVM: NoteVM
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
        database.child(TRAININGS).child(noteVM.date).child(PLACES).get().addOnSuccessListener {
            if (it.exists()) {
                val places = it.value.toString().toInt()
                if (places > 0) {
                    database.child(TRAININGS).child(noteVM.date).child(PLACES)
                        .setValue(places - 1)
                    database.child(USED_TICKETS).child(MyCrypt.encrypt(phone).toString())
                        .child(REMAINED).get().addOnSuccessListener {
                            if (it.exists()) {
                                val remained = it.value.toString().toInt()
                                if (remained > 0) {
                                    database.child(USED_TICKETS)
                                        .child(MyCrypt.encrypt(phone).toString())
                                        .child(REMAINED).setValue(remained + 1)
                                    val id = UUID.randomUUID().toString()
                                    val notesDb = database.child(NOTES).child(id)
                                    notesDb.child(COACH).setValue(noteVM.coach)
                                    notesDb.child(MAIL).setValue(mail)
                                    notesDb.child(NAME).setValue(name)
                                    notesDb.child(PHONE).setValue(phone)
                                    notesDb.child(PRICE).setValue(noteVM.price)
                                    notesDb.child(TIME).setValue(noteVM.date)
                                    notesDb.child(TITLE).setValue(noteVM.title)
                                    appToast("Успех")
                                    noted = true
                                    binding.noteBtn.text = "Вы записаны"
                                } else {
                                    appToast("Перезайдите в приложение")
                                }
                            }
                        }
                } else {
                    appToast("Перезайдите в приложение")
                }
            }
        }
    }

    private fun initFields() {
        noteVM = ViewModelProvider(APP)[NoteVM::class.java]
        setData()
    }

    private fun setData() {
        binding.title.text = noteVM.title
        binding.time.text =
            SimpleDateFormat("dd.MM.yy HH:mm").format(noteVM.date.toLong() * 1000).toString()
        binding.price.text = "${noteVM.price} ₽"
        binding.coach.text = "Тренер: ${noteVM.coach}"
    }
}