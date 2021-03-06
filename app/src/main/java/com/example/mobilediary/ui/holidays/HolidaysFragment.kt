package com.example.mobilediary.ui.holidays

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobilediary.*
import com.example.mobilediary.database.AppDatabase
import com.example.mobilediary.database.Holiday
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

        return inflater.inflate(R.layout.fragment_holidays, container, false)
    }

    override fun onStart() {
        super.onStart()

        fillRecycleView(AppDatabase(requireContext()))
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
        startActivity<EventActivity> {
            putExtra("intent_entity", "holiday")
            putExtra("id_record", list[position].idHoliday)
        }
    }

    override fun onDeleteClick(position: Int) {
        val db = AppDatabase(requireContext())

        db.holidayUserDao().deleteHoliday(
                Holiday(idHoliday = list[position].idHoliday,
                        title = list[position].title,
                        description = list[position].description,
                        date = list[position].date)
        )

        fillRecycleView(db)

        toast(getString(R.string.record_removed))
    }

    private fun fillRecycleView(database: AppDatabase) {
        setHasOptionsMenu(true)

        val eventRecyclerView: RecyclerView = requireView().findViewById(R.id.recycleViewHolidays)
        val imageView: ImageView = requireView().findViewById(R.id.imageViewHolidays)

        eventRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        list = database.holidayUserDao().getAllHolidays()

        if (list.count() > 0) {
            eventRecyclerView.adapter = HolidaysCustomRecycleAdapter(list, this)
            eventRecyclerView.visibility = View.VISIBLE
            imageView.visibility = View.GONE
        } else {
            imageView.visibility = View.VISIBLE
            eventRecyclerView.visibility = View.GONE

            imageView.setImageResource(R.drawable.ic_baseline_inbox_24)
        }
    }
}