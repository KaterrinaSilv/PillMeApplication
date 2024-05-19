package ru.mirea.pillmeapplication


import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


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
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tvName.text = pillList[position].name
//        holder.tvTime.text = pillList[position].favoritePasta
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

