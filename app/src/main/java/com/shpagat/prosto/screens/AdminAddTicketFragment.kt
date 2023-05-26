package com.shpagat.prosto.screens

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.shpagat.prosto.databinding.FragmentAdminAddTicketBinding
import com.shpagat.prosto.model.UsedTicketModel
import com.shpagat.prosto.utils.*
import com.shpagat.prosto.viewmodel.AdminVM
import com.shpagat.prosto.viewmodel.NoteVM
import java.util.*

class AdminAddTicketFragment : Fragment() {
    private lateinit var binding: FragmentAdminAddTicketBinding
    private lateinit var adminVM: AdminVM
    private lateinit var noteVM: NoteVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdminAddTicketBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFields()
        initFuns()
    }

    private fun initFields() {
        adminVM = ViewModelProvider(APP)[AdminVM::class.java]
        noteVM = ViewModelProvider(APP)[NoteVM::class.java]
        if (adminVM.editTicket) setData()
    }

    private fun setData() {
        binding.title.setText(adminVM.ticketTitle)
        binding.remained.setText(adminVM.ticketRemained)
        binding.name.setText(adminVM.ticketName)
        binding.phone.setText(adminVM.ticketPhone)
        binding.mail.setText(adminVM.ticketMail)
    }

    private fun initFuns() {
        if (adminVM.editTicket) {
            binding.addBtn.text = "Сохранить"
            binding.addBtn.setOnClickListener {
                trySaveTicket()
            }
        } else {
            binding.addBtn.text = "Добавить"
            binding.addBtn.setOnClickListener {
                tryAddTicket()
            }
        }
    }

    private fun trySaveTicket() {
        val title = myGetText(binding.title)
        val remained = myGetText(binding.remained)
        val name = myGetText(binding.name)
        val phone = myGetText(binding.phone)
        val mail = myGetText(binding.mail)
        if (title.isNotEmpty() && remained.isNotEmpty() && name.isNotEmpty() && phone.isNotEmpty() && mail.isNotEmpty()) {
            if (validateEmail(mail) && validatePhone(phone)) {
                adminVM.editTicket = false
                val ticketDb = database.child(USED_TICKETS).child(adminVM.ticketId)
                ticketDb.child(TITLE).setValue(title)
                ticketDb.child(REMAINED).setValue(remained)
                ticketDb.child(NAME).setValue(name)
                ticketDb.child(PHONE).setValue(MyCrypt.encrypt(phone))
                ticketDb.child(MAIL).setValue(MyCrypt.encrypt(mail))
                adminVM.usedTickets.add(
                    UsedTicketModel(
                        title, remained, name, phone, mail, adminVM.ticketId
                    )
                )
                noteVM.usedTickets.add(
                    UsedTicketModel(
                        title, remained, name, phone, mail, adminVM.ticketId
                    )
                )
                try {
                    for (i in adminVM.usedTickets) {
                        if (i.id == adminVM.ticketId)
                            adminVM.usedTickets.remove(i)
                    }
                    for (i in noteVM.usedTickets) {
                        if (i.id == adminVM.ticketId)
                            noteVM.usedTickets.remove(i)
                    }

                } catch (e: java.lang.Exception) {
                    Log.e("", e.message.toString())
                }
                appToast("Успех")
                clearInputs()
            } else {
                appToast("Проверьте формат почты и телефона")
            }
        } else {
            appToast("Заполните поля")
        }
    }

    private fun tryAddTicket() {
        val title = myGetText(binding.title)
        val remained = myGetText(binding.remained)
        val name = myGetText(binding.name)
        val phone = myGetText(binding.phone)
        val mail = myGetText(binding.mail)
        if (title.isNotEmpty() && remained.isNotEmpty() && name.isNotEmpty() && phone.isNotEmpty() && mail.isNotEmpty()) {
            val id = UUID.randomUUID().toString()
            val ticketDb = database.child(USED_TICKETS).child(id)
            ticketDb.child(TITLE).setValue(title)
            ticketDb.child(REMAINED).setValue(remained)
            ticketDb.child(NAME).setValue(name)
            ticketDb.child(PHONE).setValue(MyCrypt.encrypt(phone))
            ticketDb.child(MAIL).setValue(MyCrypt.encrypt(mail))
            adminVM.usedTickets.add(UsedTicketModel(title, remained, name, phone, mail, id))
            noteVM.usedTickets.add(UsedTicketModel(title, remained, name, phone, mail, id))
            appToast("Успех")
            clearInputs()
        } else {
            appToast("Заполните поля")
        }
    }

    private fun clearInputs() {
        binding.title.text.clear()
        binding.remained.text.clear()
        binding.name.text.clear()
        binding.phone.text.clear()
        binding.mail.text.clear()
    }
}