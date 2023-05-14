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
import com.shpagat.prosto.model.DirectionModel
import com.shpagat.prosto.utils.APP
import com.shpagat.prosto.viewmodel.DirectionVM

class DirectionsAdapter : RecyclerView.Adapter<DirectionsAdapter.ViewHolder>() {
    private val privateList = mutableListOf<DirectionModel>()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView = view.findViewById(R.id.item_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_direction, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = privateList[position].title
    }

    override fun getItemCount(): Int = privateList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<DirectionModel>) {
        privateList.clear()
        privateList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onViewAttachedToWindow(holder: ViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.itemView.setOnClickListener {
            goToDirection(privateList[holder.adapterPosition])
        }
    }

    private fun goToDirection(direction: DirectionModel) {
        val directionVM = ViewModelProvider(APP)[DirectionVM::class.java]
        directionVM.title = direction.title
        directionVM.desc = direction.desc
        val navController = Navigation.findNavController(APP, R.id.main_frame)
        navController.navigate(R.id.action_about_to_direction)
    }
}