package com.example.mobilediary.ui.event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.mobilediary.R
import com.example.mobilediary.SettingsActivity
import com.example.mobilediary.database.AppDatabase
import com.example.mobilediary.database.Event
import com.example.mobilediary.startActivity
import com.example.mobilediary.toast
import com.example.mobilediary.ui.adapters.EventsCustomRecyclerAdapter

class EventFragment : Fragment(), EventsCustomRecyclerAdapter.OnItemClickListener {
    private lateinit var eventViewModel: EventViewModel
    lateinit var list: List<Event>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)

        eventViewModel =
            ViewModelProvider(this).get(EventViewModel::class.java)

        return inflater.inflate(R.layout.fragment_events, container, false)
    }

    override fun onStart() {
        super.onStart()
        setHasOptionsMenu(true)

        val eventRecyclerView: RecyclerView = requireView().findViewById(R.id.recycleViewEvents)
        val imageView: ImageView = requireView().findViewById(R.id.imageView1)

        val db = AppDatabase(requireContext())

        list = db.eventUserDao().getAllEvents()

        if (list.count() > 0) {
            eventRecyclerView.adapter = EventsCustomRecyclerAdapter(list, this)
            imageView.visibility = View.GONE
        } else {
            imageView.visibility = View.VISIBLE
            eventRecyclerView.visibility = View.GONE

            imageView.setImageResource(R.drawable.ic_baseline_inbox_24)
        }
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

    override fun onDeleteClick(position: Int) {
        val db = AppDatabase(requireContext())
        db.eventUserDao().deleteEvent(
                Event(idEvent = list[position].idEvent,
                        title = list[position].title,
                        description = list[position].description,
                        date = list[position].date)
        )

        setHasOptionsMenu(true)

        val eventRecyclerView: RecyclerView = requireView().findViewById(R.id.recycleViewEvents)
        val imageView: ImageView = requireView().findViewById(R.id.imageView1)

        list = db.eventUserDao().getAllEvents()

        if (list.count() > 0) {
            eventRecyclerView.adapter = EventsCustomRecyclerAdapter(list, this)
            imageView.visibility = View.GONE
        } else {
            imageView.visibility = View.VISIBLE
            eventRecyclerView.visibility = View.GONE

            imageView.setImageResource(R.drawable.ic_baseline_inbox_24)
        }

        toast(getString(R.string.record_removed))
    }
}