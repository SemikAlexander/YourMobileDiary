package com.example.mobilediary.ui.holidays

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HolidaysViewModel: ViewModel()  {
    private val _text = MutableLiveData<String>().apply {
        value = "This is holidays Fragment"
    }
    val text: LiveData<String> = _text
}