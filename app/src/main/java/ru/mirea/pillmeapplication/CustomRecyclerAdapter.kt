package ru.mirea.pillmeapplication


import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.getSystemService
import androidx.recyclerview.widget.RecyclerView
import ru.mirea.pillmeapplication.roomDB.Pill


class CustomRecyclerAdapter(private val context: Context, private var pillList: List<Pill>) :
    RecyclerView.Adapter<CustomRecyclerAdapter.MyViewHolder>(), View.OnClickListener {



    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tv_name_pill)
        val tvTime: TextView = itemView.findViewById(R.id.tv_time_pill)
        val btnStatus: Button = itemView.findViewById(R.id.btnStatus)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recyclerview_item, parent, false)
        itemView.setOnClickListener(this)

        return MyViewHolder(itemView)

    }


    var rowIndex: Int? = null

    lateinit var alarmIntent: PendingIntent
    var alarmManager: AlarmManager? = null
    val calendar = Calendar.getInstance()
    lateinit var nextTime: List<String>
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {


        alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmIntent = Intent(context, AlarmReceiver::class.java).let { intent ->
            intent.putExtra("id", pillList[position].id)
            intent.putExtra("name", pillList[position].name)

            PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        }
        nextTime = pillList[position].nextTime!!.split(":")
        calendar.set(Calendar.HOUR_OF_DAY, nextTime[0].toInt())
        calendar.set(Calendar.MINUTE, nextTime[1].toInt())
        alarmManager?.setExact(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            alarmIntent
        )

        nextTime = pillList[position].nextTime!!.split(":")
        calendar.set(Calendar.HOUR_OF_DAY, nextTime[0].toInt())
        calendar.set(Calendar.MINUTE, nextTime[1].toInt())
        alarmManager?.setExact(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            alarmIntent
        )


        holder.tvName.text = pillList[position].name
        holder.tvTime.text = pillList[position].time
        holder.itemView.setOnClickListener {
            rowIndex = position
            notifyDataSetChanged()
        }




    }

    override fun getItemCount(): Int {
        return pillList.size
    }

    override fun onClick(v: View?) {
        println("onClick")
    }
}

