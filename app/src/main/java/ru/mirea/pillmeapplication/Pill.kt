package ru.mirea.pillmeapplication

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

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
    val period: String
)

