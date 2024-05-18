package ru.mirea.pillmeapplication

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.activityViewModels
import ru.mirea.pillmeapplication.databinding.Fragment2Binding

class FragmentTwo: BaseFragment(R.layout.fragment_2) {
    private lateinit var binding: Fragment2Binding
    private lateinit var textNum: String
    private lateinit var etNum: EditText

    private val dataModel: DataModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = Fragment2Binding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        etNum = binding.etNum
        binding.btnSbmt2.setOnClickListener {

            if(etNum.text != null){
                textNum = etNum.text.toString()
                dataModel.messageNum.value = textNum
            }
        }

    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentOne()
    }

}