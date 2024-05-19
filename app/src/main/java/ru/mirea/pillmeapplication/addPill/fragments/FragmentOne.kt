package ru.mirea.pillmeapplication.addPill.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import ru.mirea.pillmeapplication.R
import ru.mirea.pillmeapplication.addPill.BaseFragment
import ru.mirea.pillmeapplication.addPill.DataModel
import ru.mirea.pillmeapplication.databinding.Fragment1Binding

class FragmentOne : BaseFragment(R.layout.fragment_1) {
    private lateinit var binding: Fragment1Binding

    private lateinit var btnSbmt: Button

    private lateinit var name: String
    private lateinit var pillForm: String
    private lateinit var pillNum: String
    private lateinit var pillDose: String
    private lateinit var pillDoseMean: String
    private lateinit var pillEatMean: String
    private lateinit var pillComment: String

    private lateinit var newPillValueList: List<String>

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

        btnSbmt = binding.btnContinueAddPill1
        btnSbmt.setOnClickListener {

        }
        binding.btnContinueAddPill1.setOnClickListener {
            init()

            val allFieldsNotEmpty = newPillValueList.all { it.isNotEmpty() }

            if (!allFieldsNotEmpty) {
                Toast.makeText(context, "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show()
            } else {
                dataModel.newPill.value = newPillValueList
            }
        }

    }

    fun init() {
        name = binding.etPillName.text.toString()
        pillForm = binding.etPillForm.selectedItem.toString()
        pillNum = binding.etPillNum.text.toString()
        pillDose = binding.etPillDose.text.toString()
        pillDoseMean = binding.etDoseMean.selectedItem.toString()
        pillEatMean = binding.etPillEatMean.selectedItem.toString()
        pillComment = binding.etPillComment.text.toString()

        newPillValueList =
            listOf(name, pillForm, pillNum, pillDose, pillDoseMean, pillEatMean, pillComment)

    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentOne()
    }
}


