package com.shpagat.prosto.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shpagat.prosto.R
import com.shpagat.prosto.model.StatisticsModel

class StatisticsAdapter : RecyclerView.Adapter<StatisticsAdapter.ViewHolder>() {
    private val privateList = mutableListOf<StatisticsModel>()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView = view.findViewById(R.id.item_title)
        var visiting: TextView = view.findViewById(R.id.item_visitings)
        var percent: TextView = view.findViewById(R.id.item_percent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_statistic, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = privateList[position].title
        holder.visiting.text = "Количество посещений: ${privateList[position].visiting}"
        holder.percent.text = "Процентное соотношение: ${privateList[position].percent} %"
    }

    override fun getItemCount(): Int = privateList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<StatisticsModel>) {
        privateList.clear()
        privateList.addAll(list)
        notifyDataSetChanged()
    }
}