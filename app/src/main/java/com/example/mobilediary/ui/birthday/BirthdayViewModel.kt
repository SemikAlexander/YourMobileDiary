package com.example.mobilediary.ui.birthday

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BirthdayViewModel: ViewModel()  {
    private val _text = MutableLiveData<String>().apply {
        value = "This is birthday Fragment"
    }
    val text: LiveData<String> = _text
}