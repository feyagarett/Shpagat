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
                "Школа шпагата",
                "Школа Шпагата - Это целый тренировочный комплекс, направленный на достижение всех видов шпагатов.\n" +
                        "\n" +
                        "Программа занятий основывается на разработанной методике нашей студии, благодаря чему тренировки эффективны и уникальны.\n" +
                        "\n" +
                        "На протяжении всего занятия, тренер уделяет внимание каждому ученику, прорабатывая с ним упражнения.\n" +
                        "\n" +
                        "Это направление подходит для тех, кто без опыта или с начальной степенью подготовки.\n" +
                        "\n" +
                        "Тренер подробно рассказывает о технике выполнения, пользе упражнений и дыхании.\n" +
                        "Ответит на вопросы и разъяснит ошибки в упражнениях.⠀"
            )
        )
        directionVM.directions.add(
            DirectionModel(
                "Кундалини йога",
                "Кундалини-йога позволяет путём интенсивных дыхательных и двигательных упражнений «разбудить» энергию в теле человека и запустить её интенсивное движение.\n" +
                        "\n" +
                        "«Кудалини» в переводе с санскрита означает «спиральная змея». Согласно древнему учению человек рождается с энергией, сконцентрированной у основания позвоночника.\n" +
                        "\n" +
                        "Кундалини включает техники всех направлений йоги, что делает ее универсальной. Но главное преимущество данного вида йоги заключается в быстром действии. Уже за десять минут практики можно приободриться или, наоборот, успокоится. Кундалини доступна и понятна каждому."
            )
        )
        directionVM.directions.add(
            DirectionModel(
                "Здоровая спина",
                "Упражнения, направленные на развитие гибкости, повышение эластичности мышц, подвижности суставов и укрепления спины.\n" +
                        "\n" +
                        "В результате занятий накопившаяся напряжённость в мышцах сменяется легкостью, подвижностью, эластичностью и тело становится гибким и пластичным.\n" +
                        "\n" +
                        "Заниматься можно в любом возрасте, независимо от имеющегося уровня подготовки.⠀"
            )
        )
        directionVM.directions.add(
            DirectionModel(
                "Aero",
                "Aero - это комплексная растяжка всего тела с использованием воздушного гамака. Во время тренировки идёт акцент на общую растяжку и укрепление мышц спины, а так же на растяжку в целях посадки на все виды шпагата. Во время тренировки работают мышцы пресса и ног.\n" +
                        "\n" +
                        "Рекомендуемый последний прием пищи - 1,5 часа до начала занятия."
            )
        )
        directionVM.directions.add(
            DirectionModel(
                "Full body",
                "Упражнения, ориентированные на все группы мышц. Работа с собственным весом, фитнес-резинками, гантелями, скакалками, а так же элементами из кроссфита.\n" +
                        "\n" +
                        "Тренировка подходит тем, кто хочет избавиться от лишнего веса и/или привести (либо поддерживать) тело и мышцы в тонусе."
            )
        )
        directionVM.directions.add(
            DirectionModel(
                "Yoga balance",
                "В основе занятия - упражнения из йоги, пилатеса, тай-чи и др.\n" +
                        "\n" +
                        "Тренировка направлена на развитие гибкости, координации и баланса, укрепление мышечного корсета.\n" +
                        "\n" +
                        "Тренировка, которая приводит тело, разум и сознание в состояние равновесия и гармонии."
            )
        )
        directionVM.directions.add(
            DirectionModel(
                "MFT + stretch",
                "Миофасциальный релиз - это мануальная терапия, которая используется в фитнесе для расслабления мышц и фасций с помощью массажного ролла путем надавливания и растягивания тканей в сочетании с правильным дыханием.\n" +
                        "\n" +
                        "В результате улучшается кровоток и лимфоток, увеличивается диапазон движения в суставах, уходит боль и появляется чувство легкости."
            )
        )
        directionVM.directions.add(
            DirectionModel(
                "Simetrica",
                "SIMETRICA - это уникальная система физических практик, состоящая из соразмерных движений рук и ног с постепенным усилением амплитуды и глубокой проработки техники движений мышц всего тела и развитие подвижности суставов.\n" +
                        "\n" +
                        "SIMETRICA - это танец!\n" +
                        "\n" +
                        "Направление подходит для тех, кто хочет улучшить качество своих движений в фитнесе, танцах и конечно в жизни, для всех уровней подготовленности и возрастов!"
            )
        )
        directionVM.directions.add(
            DirectionModel(
                "Гвоздестояние",
                "Гвоздестояние — это лучший массаж стоп, который можно придумать!\n" +
                        "⠀\n" +
                        "Стояние на доске садху — это и аскеза, и тренировка воли, и влияние на энергетические потоки, и практика осознанности, которая способствует обновлению ментальных и двигательных паттернов.\n" +
                        "⠀\n" +
                        "Первоначально гвоздестояние было йогическим ритуалом, который позволял тренировать дух через тело. Но не все йоги стоят на гвоздях, и не все стоящие на гвоздях — йоги"
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