package ru.mirea.pillmeapplication.main.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.mirea.pillmeapplication.R
import ru.mirea.pillmeapplication.main.CustomRecyclerAdapter
import ru.mirea.pillmeapplication.roomDB.MainDb
import ru.mirea.pillmeapplication.roomDB.Pill


class PillListFragment : Fragment() {

    private val TAG: String = this::class.java.name
    private var pillList: List<Pill> = mutableListOf()

    private lateinit var rvAllPills: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_pill_list, container, false)

        rvAllPills = view.findViewById(R.id.rvAllPills)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = MainDb.getDb(requireContext())
        // все лекарства
        db.getDao().getAllItems().asLiveData().observe(viewLifecycleOwner) {
            rvAllPills.layoutManager = LinearLayoutManager(requireContext())
            rvAllPills.adapter = CustomRecyclerAdapter(requireContext(), it)
            rvAllPills.adapter?.notifyDataSetChanged()
        }
    }
}
