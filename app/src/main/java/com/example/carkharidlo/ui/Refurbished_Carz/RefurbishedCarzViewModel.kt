package com.example.carkharidlo.ui.Refurbished_Carz

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RefurbishedCarzViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Refurbished Fragment"
    }
    val text: LiveData<String> = _text
}