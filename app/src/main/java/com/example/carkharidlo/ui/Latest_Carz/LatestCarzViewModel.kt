package com.example.carkharidlo.ui.Latest_Carz

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LatestCarzViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Latest Car Fragment"
    }
    val text: LiveData<String> = _text
}