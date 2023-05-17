package com.shpagat.prosto.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.shpagat.prosto.R
import com.shpagat.prosto.model.TrainingModel
import com.shpagat.prosto.utils.APP
import com.shpagat.prosto.viewmodel.NoteVM
import java.text.SimpleDateFormat

class TrainingsAdapter : RecyclerView.Adapter<TrainingsAdapter.ViewHolder>() {
    private val privateList = mutableListOf<TrainingModel>()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView = view.findViewById(R.id.item_title)
        var date: TextView = view.findViewById(R.id.item_date)
        var coach: TextView = view.findViewById(R.id.item_coach)
        var price: TextView = view.findViewById(R.id.item_price)
        var places: TextView = view.findViewById(R.id.item_places)
        var tablet: LinearLayout = view.findViewById(R.id.item_tablet)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_training, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (privateList[position].places.toInt() == 0) holder.tablet.alpha = 0.5f
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

    override fun onViewAttachedToWindow(holder: ViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.itemView.setOnClickListener {
            if (privateList[holder.adapterPosition].places.toInt() > 0)
                goToNote(privateList[holder.adapterPosition])
        }
    }

    private fun goToNote(training: TrainingModel) {
        val noteVM = ViewModelProvider(APP)[NoteVM::class.java]
        noteVM.title = training.title
        noteVM.coach = training.coach
        noteVM.price = training.price
        noteVM.date = training.date
        val navController = Navigation.findNavController(APP, R.id.main_frame)
        navController.navigate(R.id.action_schedule_to_note)
    }
}