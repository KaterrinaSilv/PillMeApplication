package ru.mirea.pillmeapplication


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import ru.mirea.pillmeapplication.addPill.DataModel
import ru.mirea.pillmeapplication.addPill.MyViewPager2Adapter
import ru.mirea.pillmeapplication.databinding.ActivityPillAddCardBinding

class PillAddCard : AppCompatActivity() {

    private lateinit var binding: ActivityPillAddCardBinding
    private val TAG: String = this::class.java.name
    private val dataModel: DataModel by viewModels()

    private var newPill1: List<String> = listOf()
    private var newPill2: List<String> = listOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPillAddCardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var view_pager_2: ViewPager2 = binding.viewPager2

        view_pager_2.adapter = MyViewPager2Adapter(this)
        binding.btnContinueAddPill.setOnClickListener {
            var item = view_pager_2.currentItem
            if (item != 1) {
                view_pager_2.setCurrentItem(item + 1)
            } else {
                var intent = Intent(this, MainActivity::class.java)

                val db = MainDb.getDb(this)

                val pill = Pill(
                    null,
                    newPill1[0],
                    newPill1[1],
                    newPill1[2],
                    newPill1[3],
                    newPill1[4],
                    newPill1[5],
                    newPill1[6],
                    newPill2[0],
                    newPill2[1],
                    newPill2[2],
                    newPill2[3],
                    newPill2[4].toInt(),
                    newPill2[5]
                )
                Thread {
                    Log.d("onSaveClick", "addToDb")
                    db.getDao().insertItem(pill)
                }.start()

                startActivity(intent)

            }
        }
        var tab_layout: TabLayout = binding.tabLayout
        TabLayoutMediator(tab_layout, view_pager_2) { tab, position ->
            tab.text = when (position) {
                0 -> "Новый препарат"
                1 -> "Настройка приема"
                else -> throw IllegalStateException()
            }
        }.attach()

        dataModel.newPill.observe(this) {
            newPill1 = it
        }

        dataModel.pillReception.observe(this) {
            newPill2 = it
        }
    }

}