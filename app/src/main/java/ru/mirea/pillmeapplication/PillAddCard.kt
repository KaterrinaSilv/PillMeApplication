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

    private var name: String = ""
    private var pasta: String = ""
    private var num: String = ""

//    private lateinit var pill: Pill



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPillAddCardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var view_pager_2: ViewPager2 = binding.viewPager2

        view_pager_2.adapter = MyViewPager2Adapter(this)
        binding.btnContinueAddPill.setOnClickListener {
            var item = view_pager_2.currentItem
            if(item != 1) {
                view_pager_2.setCurrentItem(item + 1)
            } else {
                var intent = Intent(this, MainActivity::class.java)
                intent.putExtra("name", name)
                intent.putExtra("num", num)
                startActivity(intent)

            }
        }
        var tab_layout: TabLayout = binding.tabLayout
        TabLayoutMediator(tab_layout, view_pager_2) { tab, position ->
            tab.text = when(position) {
                0 -> "Первый"
                1 -> "Второй"
                else -> throw IllegalStateException()
            }
        }.attach()

        dataModel.messageName.observe(this){
            Log.d(TAG, "Name $it")
            name = it
        }
        dataModel.messageNum.observe(this){
            Log.d(TAG, "Num $it")
            num = it
        }

    }

}