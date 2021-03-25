package com.example.mobilediary.ui.birthday

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.mobilediary.*
import com.example.mobilediary.database.AppDatabase
import com.example.mobilediary.database.Birthday
import com.example.mobilediary.ui.adapters.BirthdaysCustomRecyclerAdapter

class BirthdayFragment : Fragment(), BirthdaysCustomRecyclerAdapter.OnItemClickListener {
    private lateinit var birthdayViewModel: BirthdayViewModel
    lateinit var list: List<Birthday>

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)

        birthdayViewModel =
                ViewModelProvider(this).get(BirthdayViewModel::class.java)
        return inflater.inflate(R.layout.fragment_birthdays, container, false)
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
            putExtra("intent_entity", "birthday")
            putExtra("id_record", list[position].idBirthday)
        }
    }

    override fun onDeleteClick(position: Int) {
        val db = AppDatabase(requireContext())
        db.birthdayUserDao().deleteBirthday(
                Birthday(idBirthday = list[position].idBirthday,
                        namePerson = list[position].namePerson,
                        date = list[position].date)
        )

        fillRecycleView(db)

        toast(getString(R.string.record_removed))
    }

    private fun fillRecycleView(database: AppDatabase) {
        setHasOptionsMenu(true)

        val eventRecyclerView: RecyclerView = requireView().findViewById(R.id.recycleViewBirthdays)
        val imageView: ImageView = requireView().findViewById(R.id.imageViewBirthdays)

        list = database.birthdayUserDao().getAllBirthday()

        if (list.count() > 0) {
            eventRecyclerView.adapter = BirthdaysCustomRecyclerAdapter(list, this)
            eventRecyclerView.visibility = View.VISIBLE
            imageView.visibility = View.GONE
        } else {
            imageView.visibility = View.VISIBLE
            eventRecyclerView.visibility = View.GONE

            imageView.setImageResource(R.drawable.ic_baseline_inbox_24)
        }
    }
}