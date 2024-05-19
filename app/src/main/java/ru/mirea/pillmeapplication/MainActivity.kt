package ru.mirea.pillmeapplication


import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.mirea.pillmeapplication.databinding.ActivityMainBinding
import java.time.LocalDate
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var rvPills: RecyclerView
    private lateinit var btnAdd: Button

    private val TAG: String = this::class.java.name

    private var pillList: List<Pill> = mutableListOf()




    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val today = LocalDate.now()
        val calendar = convertDateToCalendar(today.toString())

        val year = calendar.get(Calendar.YEAR)
        val month = getMonthFormat(calendar.get(Calendar.MONTH) + 1)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        binding.tvMonth.text = "$day $month $year"


        val db = MainDb.getDb(this)

        rvPills = binding.rvPills

        // все лекарства
//        db.getDao().getAllItems().asLiveData().observe(this){
//
//            rvPills.layoutManager = LinearLayoutManager(this)
//            rvPills.adapter = CustomRecyclerAdapter(this, it)
//            rvPills.adapter?.notifyDataSetChanged()
//        }

        // лекарства на сегодня
        db.getDao().getAllItemsForToday().asLiveData().observe(this){

            rvPills.layoutManager = LinearLayoutManager(this)
            rvPills.adapter = CustomRecyclerAdapter(this, it)
            rvPills.adapter?.notifyDataSetChanged()
        }

        btnAdd = binding.btnAdd

        btnAdd.setOnClickListener {
            Log.d(TAG, "button Add clicked")
            val intent = Intent(this, PillAddCard::class.java)
            startActivity(intent)
        }

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
