package ru.mirea.pillmeapplication.roomDB

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "pills")
data class Pill(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int? = null,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "pill_form")
    val pillForm: String,

    @ColumnInfo(name = "pill_num")
    val pillNum: String,

    @ColumnInfo(name = "pill_dose")
    val pillDose: String,

    @ColumnInfo(name = "pill_dose_mean")
    val pillDoseMean: String,

    @ColumnInfo(name = "pill_eat_mean")
    val pillEatMean: String,

    @ColumnInfo(name = "pill_comment")
    val pillComment: String,

    @ColumnInfo(name = "time")
    val time: String,

    @ColumnInfo(name = "time_before")
    val timeBefore: String,

    @ColumnInfo(name = "date_start")
    val dateStart: String,

    @ColumnInfo(name = "date_end")
    val dateEnd: String,

    @ColumnInfo(name = "period")
    var period: Int = 1,

    @ColumnInfo(name = "periodMean")
    val periodMean: String,

    @ColumnInfo(name = "next_date")
    var nextDate: String? = null,

    @ColumnInfo(name = "next_time")
    var nextTime: String? = null,

    @ColumnInfo(name = "status")
    var status: Boolean? = false
) {
    init {
        periodConvert()
        calculateNextIntake()

    }

    private fun periodConvert() {
        if (periodMean == "день") {
            period *= 24 * 60 * 60 * 1000
        } else if (periodMean == "час") {
            period *= 60 * 60 * 1000
        } else {
            period *= 60 * 1000
        }
    }

    private fun calculateNextIntake() {
        val startDateCalendar = convertDateToCalendar(dateStart)

        val timeParts = time.split(":")
        val hours = timeParts[0].toInt()
        val minutes = timeParts[1].toInt()


        startDateCalendar.set(Calendar.HOUR_OF_DAY, hours)
        startDateCalendar.set(Calendar.MINUTE, minutes)

        // Добавить периодичность к календарю (в миллисекундах)
        val periodicityInMilliseconds = period
        startDateCalendar.add(Calendar.MILLISECOND, periodicityInMilliseconds)

        val month = startDateCalendar.get(Calendar.MONTH) + 1
        var monthStr: String
        if (month<10){
            monthStr = "0$month"
        } else {
            monthStr = month.toString()
        }


        // Получить следующую дату и время
        nextDate = startDateCalendar.get(Calendar.YEAR).toString() + "-" +
                monthStr + "-" +
                startDateCalendar.get(Calendar.DAY_OF_MONTH).toString()
        var timeBeforeMean: Int
        if(timeBefore == "за 5 минут"){
            timeBeforeMean = 5
        } else if (timeBefore == "за 10 минут"){
            timeBeforeMean = 10
        } else {
            timeBeforeMean = 30
        }
        nextTime = startDateCalendar.get(Calendar.HOUR_OF_DAY).toString() + ":" +
                (startDateCalendar.get(Calendar.MINUTE) - timeBeforeMean).toString()

        status = false

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


