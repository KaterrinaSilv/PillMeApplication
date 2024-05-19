package ru.mirea.pillmeapplication


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.mirea.pillmeapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var rvPills : RecyclerView
    private lateinit var btnAdd: Button

    private lateinit var name:String
    private lateinit var pasta: String
    private lateinit var num: String

    private val TAG: String = this::class.java.name


    companion object {
        // Статическое поле для хранения массива
        var pillList = mutableListOf(Pill("Таблетка 1", "Спагетти", "42"), Pill("Таблетка 2", "Лазанья", "10"),  Pill("Таблетка 2", "Лазанья", "10"))

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent

        name = intent.getStringExtra("name").toString()
        pasta = intent.getStringExtra("pasta").toString()
        num = intent.getStringExtra("num").toString()

        if(name != null){
            val pill = Pill(name, pasta, num)
            pillList.add(pill)
        }

        rvPills = binding.rvPills
        rvPills.layoutManager = LinearLayoutManager(this)
        rvPills.adapter = CustomRecyclerAdapter(this, getPillList())

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

}
