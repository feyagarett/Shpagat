package com.shpagat.prosto.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.shpagat.prosto.R
import com.shpagat.prosto.model.TrainingModel
import com.shpagat.prosto.utils.APP
import com.shpagat.prosto.viewmodel.AdminVM
import java.text.SimpleDateFormat

class AdminTrainingsAdapter : RecyclerView.Adapter<AdminTrainingsAdapter.ViewHolder>() {
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

    @SuppressLint("NotifyDataSetChanged")
    fun deleteItem(position: Int) {
        privateList.removeAt(position)
        notifyDataSetChanged()
    }

    fun getItem(position: Int) = privateList[position]

    override fun onViewAttachedToWindow(holder: ViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.itemView.setOnClickListener {
            editTraining(privateList[holder.absoluteAdapterPosition])
        }
    }

    private fun editTraining(training: TrainingModel) {
        val adminVM = ViewModelProvider(APP)[AdminVM::class.java]
        adminVM.editTraining = true
        adminVM.trainingDate = training.date
        adminVM.trainingTitle = training.title
        adminVM.trainingCoach = training.coach
        adminVM.trainingPrice = training.price
        adminVM.trainingPlaces = training.places
        val navController = Navigation.findNavController(APP, R.id.main_frame)
        navController.navigate(R.id.action_adminSchedule_to_adminAddTraining)

    }
}