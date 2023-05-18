package com.shpagat.prosto.screens

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.MPPointF
import com.shpagat.prosto.R
import com.shpagat.prosto.adapter.StatisticsAdapter
import com.shpagat.prosto.databinding.FragmentAdminStatisticsBinding
import com.shpagat.prosto.model.StatisticsModel
import com.shpagat.prosto.utils.*
import com.shpagat.prosto.viewmodel.AdminVM

class AdminStatisticsFragment : Fragment() {
    private lateinit var binding: FragmentAdminStatisticsBinding
    private lateinit var adminVM: AdminVM
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: StatisticsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdminStatisticsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFields()
    }

    private fun initFields() {
        adminVM = ViewModelProvider(APP)[AdminVM::class.java]
        setStatistics()
    }

    private fun setStatistics() {
        adminVM.statistics.clear()
        binding.amount.text = "Общее число посещений: ${adminVM.notes.size}"
        getOneDirection(SCHOOL)
        getOneDirection(KUNDALINI)
        getOneDirection(BACK)
        getOneDirection(AERO)
        getOneDirection(FULL)
        getOneDirection(JOGA)
        getOneDirection(MFR)
        getOneDirection(SIMETRICA)
        getOneDirection(PIN)
        setDiagram()
        setList()
    }

    private fun setDiagram() {
        binding.diagram.setUsePercentValues(true)
        binding.diagram.description.isEnabled = true
        binding.diagram.setExtraOffsets(5f, 10f, 5f, 5f)
        binding.diagram.dragDecelerationFrictionCoef = 0.95f
        binding.diagram.isDrawHoleEnabled = true
        binding.diagram.setHoleColor(Color.WHITE)
        binding.diagram.setTransparentCircleColor(Color.WHITE)
        binding.diagram.setTransparentCircleAlpha(110)
        binding.diagram.holeRadius = 58f
        binding.diagram.transparentCircleRadius = 61f
        binding.diagram.setDrawCenterText(true)
        binding.diagram.rotationAngle = 0f
        binding.diagram.isRotationEnabled = true
        binding.diagram.isHighlightPerTapEnabled = true
        binding.diagram.animateY(1400, Easing.EaseInOutQuad)
        binding.diagram.legend.isEnabled = false
        binding.diagram.setEntryLabelColor(Color.WHITE)
        binding.diagram.setEntryLabelTextSize(12f)
        binding.diagram.description.isEnabled = false

        val entries: ArrayList<PieEntry> = ArrayList()
        for (i in adminVM.statistics) {
            entries.add(PieEntry(i.percent.toFloat(), i.title))
        }
        val dataSet = PieDataSet(entries, "")

        dataSet.setDrawIcons(false)

        dataSet.sliceSpace = 3f
        dataSet.iconsOffset = MPPointF(0f, 40f)
        dataSet.selectionShift = 5f

        val colors: ArrayList<Int> = ArrayList()
        colors.add(resources.getColor(R.color.primary_color))
        dataSet.colors = colors
        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter())
        data.setValueTextSize(14f)
        data.setValueTypeface(Typeface.DEFAULT_BOLD)
        data.setValueTextColor(Color.WHITE)
        binding.diagram.data = data
        binding.diagram.highlightValues(null)
        binding.diagram.invalidate()
    }

    private fun setList() {
        adapter = StatisticsAdapter()
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(APP)
        recyclerView.adapter = adapter
        adapter.setList(adminVM.statistics)
    }

    private fun getOneDirection(direction: String) {
        var size = 0
        for (i in adminVM.notes) {
            if (i.title == direction) {
                size++
            }
        }
        if (size > 0) {
            val percent = size.toFloat() * 100 / adminVM.notes.size.toFloat()
            adminVM.statistics.add(
                StatisticsModel(
                    direction,
                    size.toString(),
                    percent.toInt().toString()
                )
            )
        }
    }

}