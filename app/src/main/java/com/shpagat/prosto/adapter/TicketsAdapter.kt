package com.shpagat.prosto.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shpagat.prosto.R
import com.shpagat.prosto.model.TicketModel

class TicketsAdapter : RecyclerView.Adapter<TicketsAdapter.ViewHolder>() {
    private val privateList = mutableListOf<TicketModel>()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView = view.findViewById(R.id.item_title)
        var price: TextView = view.findViewById(R.id.item_price)
        var prefPrice: TextView = view.findViewById(R.id.item_pref_price)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_ticket, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = privateList[position].title
        holder.price.text = "Цена: ${privateList[position].price}"
        holder.prefPrice.text = "Льготная цена: ${privateList[position].prefPrice}"
    }

    override fun getItemCount(): Int = privateList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<TicketModel>) {
        privateList.clear()
        privateList.addAll(list)
        notifyDataSetChanged()
    }
}