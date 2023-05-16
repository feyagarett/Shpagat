package com.shpagat.prosto.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shpagat.prosto.R
import com.shpagat.prosto.model.NoteModel
import java.text.SimpleDateFormat

class NotesAdapter : RecyclerView.Adapter<NotesAdapter.ViewHolder>() {
    private val privateList = mutableListOf<NoteModel>()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name: TextView = view.findViewById(R.id.item_name)
        var phone: TextView = view.findViewById(R.id.item_phone)
        var email: TextView = view.findViewById(R.id.item_email)
        var title: TextView = view.findViewById(R.id.item_title)
        var date: TextView = view.findViewById(R.id.item_time)
        var coach: TextView = view.findViewById(R.id.item_coach)
        var price: TextView = view.findViewById(R.id.item_price)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = privateList[position].name
        holder.phone.text = privateList[position].phone
        holder.email.text = privateList[position].email
        holder.title.text = privateList[position].title
        holder.date.text = SimpleDateFormat("HH:mm")
            .format(privateList[position].time.toLong() * 1000).toString()
        holder.price.text = privateList[position].price + " ₽"
        holder.coach.text = "Тренер: ${privateList[position].coach}"
    }

    override fun getItemCount(): Int = privateList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<NoteModel>) {
        privateList.clear()
        privateList.addAll(list)
        notifyDataSetChanged()
    }
}