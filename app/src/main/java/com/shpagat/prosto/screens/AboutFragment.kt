package com.shpagat.prosto.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shpagat.prosto.adapter.DirectionsAdapter
import com.shpagat.prosto.databinding.FragmentAboutBinding
import com.shpagat.prosto.model.DirectionModel
import com.shpagat.prosto.utils.APP
import com.shpagat.prosto.viewmodel.DirectionVM

class AboutFragment : Fragment() {
    private lateinit var binding: FragmentAboutBinding
    private lateinit var directionVM: DirectionVM
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DirectionsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAboutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        directionVM = ViewModelProvider(APP)[DirectionVM::class.java]
        getDirections()
    }

    private fun getDirections() {
        directionVM.directions.clear()
        directionVM.directions.add(
            DirectionModel(
                "AERO",
                "Это комплексная растяжка всего тела с использованием воздушного гамака. Во время тренировки идёт акцент на общую растяжку и укрепление мышц спины, а так же на растяжку в целях посадки на все виды шпагата и базовые акробатические элементы.\n" +
                        "Во время тренировки работают мышцы пресса и ног."
            )
        )
        directionVM.directions.add(
            DirectionModel(
                "FULL BODY",
                "Это силовая тренировка, где упражнения, ориентированы на все группы мышц.\n" +
                        "Работа с собственным весом, фитнес-резинками, гантелями, скакалками, утяжелителями, а так же элементами из кроссфита. Тренировка подходит тем, кто хочет избавиться от лишнего веса и привести/поддерживать тело и мышцы в тонусе."
            )
        )
        directionVM.directions.add(
            DirectionModel(
                "SIMETRICA",
                "Это уникальная система физических практик, состоящая из соразмерных движений рук и ног с постепенным усилением амплитуды и глубокой проработки техники движений мышц всего тела и развитие подвижности суставов. Направление подходит для тех, кто хочет улучшить качество своих движений в фитнесе, танцах и конечно в жизни, для всех уровней подготовленности и возрастов!"
            )
        )
        directionVM.directions.add(
            DirectionModel(
                "MFR + STRETCH",
                "Это мануальная терапия, которая используется в фитнесе для расслабления мышц и фасций с помощью массажного ролла путем надавливания и растягивания тканей в сочетании с правильным дыханием. В результате улучшается кровоток и лимфоток, качество кожи, увеличивается диапазон движения в суставах, уходит боль и появляется чувство лёгкости!"
            )
        )
        directionVM.directions.add(
            DirectionModel(
                "ХАТХА +\n" +
                        "КУНДАЛИНИ ЙОГА",
                "Классическая хатха йога основана на выполнении асан в статике в комплексе с дыханием и высокой концентрацией внимания.\n" +
                        "Кундалини-йога включает техники интенсивных дыхательных и двигательных упражнений, что улучшает концентрацию, укрепляет тело и расслабляет ум."
            )
        )
        initList()
    }

    private fun initList() {
        adapter = DirectionsAdapter()
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = GridLayoutManager(APP, 2)
        recyclerView.adapter = adapter
        adapter.setList(directionVM.directions)
    }
}