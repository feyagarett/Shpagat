package com.shpagat.prosto.utils

import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.Toast

// функция для всплывающих сообщений
fun appToast(text: String) {
    Toast.makeText(APP, text, Toast.LENGTH_SHORT).show()
}

// функция для получения текстовых данных из EditText
fun myGetText(editText: EditText) = editText.text.toString().trim()

fun gone(view: View) {
    view.visibility = View.GONE
}

fun visible(view: View) {
    view.visibility = View.VISIBLE
}

// валидация почты
fun validateEmail(mail: String): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(mail).matches()
}

// валидация номера
fun validatePhone(phone: String): Boolean {
    return Patterns.PHONE.matcher(phone).matches() && (phone.length == 12 || phone.length == 11)
}