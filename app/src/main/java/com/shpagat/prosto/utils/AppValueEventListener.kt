package com.shpagat.prosto.utils

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

// класс для считывания списочных данных из БД
class AppValueEventListener(val onSuccess: (DataSnapshot) -> Unit) : ValueEventListener {
    override fun onDataChange(snapshot: DataSnapshot) {
        onSuccess(snapshot)
    }

    override fun onCancelled(error: DatabaseError) {
    }
}