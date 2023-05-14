package com.shpagat.prosto.utils

import android.widget.Toast

fun appToast(text: String) {
    Toast.makeText(APP, text, Toast.LENGTH_SHORT).show()
}