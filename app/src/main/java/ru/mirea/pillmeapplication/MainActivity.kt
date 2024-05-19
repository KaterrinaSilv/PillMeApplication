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
    private lateinit var rvPills: RecyclerView
    private lateinit var btnAdd: Button

    private val TAG: String = this::class.java.name


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent

        var newPillResult = intent.getStringExtra("pill")
        println("OK")
        println(newPillResult)

        rvPills = binding.rvPills
        rvPills.layoutManager = LinearLayoutManager(this)
//        rvPills.adapter = CustomRecyclerAdapter(this, getPillList())

        btnAdd = binding.btnAdd

        btnAdd.setOnClickListener {
            Log.d(TAG, "button Add clicked")
            val intent = Intent(this, PillAddCard::class.java)
            startActivity(intent)
        }

    }

//    private fun getPillList(): List<Pill> {
//        return pillList
//    }

}
