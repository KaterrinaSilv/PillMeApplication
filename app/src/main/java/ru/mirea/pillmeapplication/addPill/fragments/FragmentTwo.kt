package ru.mirea.pillmeapplication.addPill.fragments

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import ru.mirea.pillmeapplication.R
import ru.mirea.pillmeapplication.addPill.BaseFragment
import ru.mirea.pillmeapplication.addPill.DataModel
import ru.mirea.pillmeapplication.databinding.Fragment2Binding

class FragmentTwo : BaseFragment(R.layout.fragment_2) {
    private lateinit var binding: Fragment2Binding

    private lateinit var btnDateStart: Button
    private lateinit var btnDateEnd: Button
    private lateinit var btnTime: Button

    private lateinit var datePickerDialog: DatePickerDialog
    private lateinit var timePickerDialog: TimePickerDialog

    private lateinit var time: String
    private lateinit var timeBefore: String
    private lateinit var dateStart: String
    private lateinit var dateEnd: String
    private lateinit var period: String

    private val dataModel: DataModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = Fragment2Binding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        init()

        initDatePicker(btnDateStart)
        btnDateStart.setText(getTodaysDate())
        btnDateStart.setOnClickListener {
            openDatePicker()
            dateStart = btnDateStart.text.toString()
        }

        initDatePicker(btnDateEnd)
        btnDateEnd.setText(getTodaysDate())
        btnDateEnd.setOnClickListener {
            openDatePicker()
            dateEnd = btnDateEnd.text.toString()
        }

        initTimePicker(btnTime)
        btnTime.setText("00:00")
        btnTime.setOnClickListener {
            openTimePicker()
            time = btnTime.text.toString()
        }


        binding.btnContinueAddPill2.setOnClickListener {
            if (binding.etPeriod.text != null && binding.etTimeBefore.selectedItem.toString() != null) {
                period = binding.etPeriod.text.toString()
                timeBefore = binding.etTimeBefore.selectedItem.toString()
                dataModel.pillReception.value = listOf(time, timeBefore, dateStart, dateEnd, period)
            } else{
                Toast.makeText(context, "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show()
            }
        }


    }

    fun init() {
        btnDateStart = binding.btnChooseDateStart
        btnDateEnd = binding.btnChooseDatEnd
        btnTime = binding.btnChooseTime

    }

    private fun getTodaysDate(): String? {
        val cal = Calendar.getInstance()
        val year = cal[Calendar.YEAR]
        var month = cal[Calendar.MONTH]
        month = month + 1
        val day = cal[Calendar.DAY_OF_MONTH]
        return makeDateString(day, month, year)
    }

    private fun initTimePicker(btn: Button) {
        val timeSetListener =
            TimePickerDialog.OnTimeSetListener{
                timePicker, hour, minute ->
                var time: String = makeTimeString(hour, minute)
                btn.setText(time)
            }

        val cal = Calendar.getInstance()
        val hour = cal[Calendar.HOUR_OF_DAY]
        val minute = cal[Calendar.MINUTE]

        val style: Int = AlertDialog.THEME_HOLO_LIGHT

        timePickerDialog = TimePickerDialog(context!!, timeSetListener, hour, minute, true)
    }


    private fun initDatePicker(btn: Button) {
        val dateSetListener =
            DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
                var month = month
                month = month + 1
                val date: String = makeDateString(day, month, year)
                btn.setText(date)
            }
        val cal = Calendar.getInstance()
        val year = cal[Calendar.YEAR]
        val month = cal[Calendar.MONTH]
        val day = cal[Calendar.DAY_OF_MONTH]

        val style: Int = AlertDialog.THEME_HOLO_LIGHT

        datePickerDialog = DatePickerDialog(context!!, dateSetListener, year, month, day)
    }

    private fun makeTimeString(hour: Int, minute: Int): String {
        return "$hour:$minute"
    }
    private fun makeDateString(day: Int, month: Int, year: Int): String {
        val monthString = getMonthFormat(month) // Assuming getMonthFormat returns a String
        return "$monthString $day $year"

    }

    private fun getMonthFormat(month: Int): Any {
        if (month == 1)
            return "янв";
        if (month == 2)
            return "февр";
        if (month == 3)
            return "март";
        if (month == 4)
            return "апр";
        if (month == 5)
            return "май";
        if (month == 6)
            return "июнь";
        if (month == 7)
            return "июль";
        if (month == 8)
            return "авг";
        if (month == 9)
            return "сент";
        if (month == 10)
            return "окт";
        if (month == 11)
            return "нояб";
        if (month == 12)
            return "дек";

        return "янв";

    }

    fun openTimePicker(){
        timePickerDialog.show()
    }
    fun openDatePicker() {
        datePickerDialog.show()
    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentOne()
    }

}