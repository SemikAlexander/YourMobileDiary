package com.example.mobilediary.ui.birthday

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mobilediary.R
import com.example.mobilediary.ui.event.EventViewModel

class BirthdayFragment : Fragment() {
    private lateinit var birthdayViewModel: BirthdayViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        birthdayViewModel =
            ViewModelProvider(this).get(BirthdayViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_events, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        birthdayViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}