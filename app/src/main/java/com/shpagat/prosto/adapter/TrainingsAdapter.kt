package com.shpagat.prosto.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shpagat.prosto.R
import com.shpagat.prosto.model.TrainingModel
import java.text.SimpleDateFormat

class TrainingsAdapter : RecyclerView.Adapter<TrainingsAdapter.ViewHolder>() {
    private val privateList = mutableListOf<TrainingModel>()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView = view.findViewById(R.id.item_title)
        var date: TextView = view.findViewById(R.id.item_date)
        var coach: TextView = view.findViewById(R.id.item_coach)
        var price: TextView = view.findViewById(R.id.item_price)
        var places: TextView = view.findViewById(R.id.item_places)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_training, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = privateList[position].title
        holder.price.text = "${privateList[position].price} ₽"
        holder.coach.text = "Тренер: ${privateList[position].coach}"
        holder.places.text = "Осталось мест: ${privateList[position].places}"
        holder.date.text = SimpleDateFormat("dd.MM.yy HH:mm")
            .format(privateList[position].date.toLong() * 1000).toString()
    }

    override fun getItemCount(): Int = privateList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<TrainingModel>) {
        privateList.clear()
        privateList.addAll(list)
        notifyDataSetChanged()
    }
}