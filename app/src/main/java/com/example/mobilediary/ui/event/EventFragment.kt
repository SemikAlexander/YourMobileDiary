package com.example.mobilediary.ui.event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.mobilediary.*
import com.example.mobilediary.database.AppDatabase
import com.example.mobilediary.database.Event
import com.example.mobilediary.ui.adapters.EventsCustomRecyclerAdapter

class EventFragment : Fragment(), EventsCustomRecyclerAdapter.OnItemClickListener {
    lateinit var list: List<Event>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)

        return inflater.inflate(R.layout.fragment_events, container, false)
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
            putExtra("intent_entity", "event")
            putExtra("id_record", list[position].idEvent)
        }
    }

    override fun onDeleteClick(position: Int) {
        val db = AppDatabase(requireContext())
        db.eventUserDao().deleteEvent(
                Event(idEvent = list[position].idEvent,
                        title = list[position].title,
                        description = list[position].description,
                        date = list[position].date)
        )

        fillRecycleView(db)

        toast(getString(R.string.record_removed))
    }

    private fun fillRecycleView(database: AppDatabase) {
        setHasOptionsMenu(true)

        val eventRecyclerView: RecyclerView = requireView().findViewById(R.id.recycleViewEvents)
        val imageView: ImageView = requireView().findViewById(R.id.imageView1)

        list = database.eventUserDao().getAllEvents()

        if (list.count() > 0) {
            eventRecyclerView.adapter = EventsCustomRecyclerAdapter(list, this)
            eventRecyclerView.visibility = View.VISIBLE
            imageView.visibility = View.GONE
        } else {
            imageView.visibility = View.VISIBLE
            eventRecyclerView.visibility = View.GONE

            imageView.setImageResource(R.drawable.ic_baseline_inbox_24)
        }
    }
}

/*
*   ???????????????? ?? ?????????? EventActivity:
* 1. ???????????????? ??????????????
* 2. Id ????????????
* */