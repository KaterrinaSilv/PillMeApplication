package ru.mirea.pillmeapplication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class DataModel : ViewModel() {
    val messageName: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val messagePasta: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val messageNum: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
}