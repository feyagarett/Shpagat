package com.shpagat.prosto.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shpagat.prosto.R
import com.shpagat.prosto.model.UsedTicketModel

class UsedTicketsAdapter : RecyclerView.Adapter<UsedTicketsAdapter.ViewHolder>() {
    private val privateList = mutableListOf<UsedTicketModel>()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView = view.findViewById(R.id.item_title)
        var remained: TextView = view.findViewById(R.id.item_remained)
        var name: TextView = view.findViewById(R.id.item_name)
        var phone: TextView = view.findViewById(R.id.item_phone)
        var email: TextView = view.findViewById(R.id.item_email)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_used_ticket, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = privateList[position].title
        holder.remained.text = "Осталось занятий: ${privateList[position].remained}"
        holder.name.text = privateList[position].name
        holder.phone.text = privateList[position].phone
        holder.email.text = privateList[position].email
    }

    override fun getItemCount(): Int = privateList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<UsedTicketModel>) {
        privateList.clear()
        privateList.addAll(list)
        notifyDataSetChanged()
    }
}