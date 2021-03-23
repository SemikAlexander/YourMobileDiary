package com.example.mobilediary.ui.holidays

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobilediary.R
import com.example.mobilediary.SettingsActivity
import com.example.mobilediary.database.AppDatabase
import com.example.mobilediary.database.Event
import com.example.mobilediary.database.Holiday
import com.example.mobilediary.startActivity
import com.example.mobilediary.toast
import com.example.mobilediary.ui.adapters.HolidaysCustomRecycleAdapter
import com.example.mobilediary.ui.event.EventViewModel

class HolidaysFragment : Fragment(), HolidaysCustomRecycleAdapter.OnItemClickListener {
    private lateinit var holidayViewModel: EventViewModel
    lateinit var list: List<Holiday>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)

        holidayViewModel =
            ViewModelProvider(this).get(EventViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_holidays, container, false)

        val eventRecyclerView: RecyclerView = root.findViewById(R.id.recycleViewHolidays)
        val imageView: ImageView = root.findViewById(R.id.imageViewHolidays)

        eventRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        val db = AppDatabase(requireContext())

        list = db.holidayUserDao().getAllHolidays()

        if (list.count() > 0) {
            eventRecyclerView.adapter = HolidaysCustomRecycleAdapter(list, this)
            imageView.visibility = View.GONE
        }
        else{
            imageView.visibility = View.VISIBLE
            eventRecyclerView.visibility = View.GONE

            imageView.setImageResource(R.drawable.ic_baseline_inbox_24)
        }

        return root
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> {
                startActivity<SettingsActivity>()
                return true
            }
        }
        return super.onContextItemSelected(item)
    }

    override fun onItemClick(position: Int) {
        toast(list[position].description)
    }
}