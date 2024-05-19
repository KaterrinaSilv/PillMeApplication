package ru.mirea.pillmeapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import ru.mirea.pillmeapplication.roomDB.Pill
import java.time.LocalDate
import android.content.Intent
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ru.mirea.pillmeapplication.roomDB.MainDb
import java.util.Calendar

class MainFragment : Fragment() {
    private lateinit var rvPills: RecyclerView
    private lateinit var btnAdd: Button

    private val TAG: String = this::class.java.name
    private var pillList: List<Pill> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        rvPills = view.findViewById(R.id.rvPills)
        btnAdd = view.findViewById(R.id.btnAdd)

        val today = LocalDate.now()
        val calendar = convertDateToCalendar(today.toString())

        val year = calendar.get(Calendar.YEAR)
        val month = getMonthFormat(calendar.get(Calendar.MONTH) + 1)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        view.findViewById<TextView>(R.id.tvMonth).text = "$day $month $year"

        val db = MainDb.getDb(requireContext())

//        // все лекарства
//        db.getDao().getAllItems().asLiveData().observe(viewLifecycleOwner) {
//            rvPills.layoutManager = LinearLayoutManager(requireContext())
//            rvPills.adapter = CustomRecyclerAdapter(requireContext(), it)
//            rvPills.adapter?.notifyDataSetChanged()
//        }

        // лекарства на сегодня
        db.getDao().getAllItemsForToday().asLiveData().observe(viewLifecycleOwner) {
            rvPills.layoutManager = LinearLayoutManager(requireContext())
            rvPills.adapter = CustomRecyclerAdapter(requireContext(), it)
            rvPills.adapter?.notifyDataSetChanged()
        }

        btnAdd.setOnClickListener {
            Log.d(TAG, "button Add clicked")
            val intent = Intent(requireContext(), PillAddCard::class.java)
            startActivity(intent)
        }


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnMedicine = view.findViewById<Button>(R.id.btnMedicine)
        val btnProfile = view.findViewById<Button>(R.id.btnProfile)
        val controller = findNavController()
        btnMedicine.setOnClickListener { controller.navigate(R.id.action_mainFragment_to_pillListFragment) }
        btnProfile.setOnClickListener { controller.navigate(R.id.action_mainFragment_to_profileFragment) }
    }

    private fun getPillList(): List<Pill> {
        return pillList
    }

    private fun getMonthFormat(month: Int): Any {
        if (month == 1)
            return "января";
        if (month == 2)
            return "февраля";
        if (month == 3)
            return "марта";
        if (month == 4)
            return "апреля";
        if (month == 5)
            return "мая";
        if (month == 6)
            return "июня";
        if (month == 7)
            return "июля";
        if (month == 8)
            return "августа";
        if (month == 9)
            return "сентября";
        if (month == 10)
            return "октября";
        if (month == 11)
            return "ноября";
        if (month == 12)
            return "декабря";
        return "января";
    }

    fun convertDateToCalendar(dateString: String): Calendar {
        // Разбить строку даты на год, месяц и день
        val parts = dateString.split("-")
        val year = parts[0].toInt()
        val month = parts[1].toInt() - 1 // Месяцы в Calendar нумеруются с 0
        val day = parts[2].toInt()

        // Создать объект Calendar и установить значения
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, day)

        return calendar
    }
}