package ru.mirea.pillmeapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.activityViewModels
import ru.mirea.pillmeapplication.databinding.Fragment1Binding

class FragmentOne : BaseFragment(R.layout.fragment_1){
    private lateinit var binding: Fragment1Binding
    private lateinit var textName: String
    private lateinit var etName: EditText

    private val dataModel: DataModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = Fragment1Binding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        etName = binding.etFragmentOne
        binding.btnSbmt.setOnClickListener {

            if(etName.text != null){
                textName = etName.text.toString()
                dataModel.messageName.value = textName
            }
        }

    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentOne()
    }
}


