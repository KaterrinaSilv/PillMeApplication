package ru.mirea.pillmeapplication.addPill

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class DataModel : ViewModel() {
    val newPill: MutableLiveData<List<String>> by lazy {
        MutableLiveData<List<String>>()
    }
    val pillReception: MutableLiveData<List<String>> by lazy {
        MutableLiveData<List<String>>()
    }

    val createPill: MutableLiveData<List<String>> by lazy {
        MutableLiveData<List<String>>()
    }
}