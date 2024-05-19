package ru.mirea.pillmeapplication.roomDB
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.mirea.pillmeapplication.roomDB.Pill

@Dao
interface Dao {

    @Insert
    fun insertItem(pill: Pill)

    @Query("SELECT * FROM pills")
    fun getAllItems(): Flow<List<Pill>>

    @Query("SELECT * FROM pills WHERE DATE(next_date) = DATE('now')")
    fun getAllItemsForToday(): Flow<List<Pill>>



}