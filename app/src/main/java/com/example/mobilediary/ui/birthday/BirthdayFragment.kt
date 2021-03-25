package com.example.mobilediary.ui.birthday

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
import com.example.mobilediary.R
import com.example.mobilediary.SettingsActivity
import com.example.mobilediary.database.AppDatabase
import com.example.mobilediary.database.Birthday
import com.example.mobilediary.startActivity
import com.example.mobilediary.toast
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
        setHasOptionsMenu(true)

        val eventRecyclerView: RecyclerView = requireView().findViewById(R.id.recycleViewBirthdays)
        val imageView: ImageView = requireView().findViewById(R.id.imageViewBirthdays)

        eventRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        val db = AppDatabase(requireContext())

        list = db.birthdayUserDao().getAllBirthday()

        if (list.count() > 0) {
            eventRecyclerView.adapter = BirthdaysCustomRecyclerAdapter(list, this)
            imageView.visibility = View.GONE
        }
        else{
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
        toast(list[position].namePerson)
    }

    override fun onDeleteClick(position: Int) {
        val db = AppDatabase(requireContext())
        db.birthdayUserDao().deleteBirthday(
                Birthday(idBirthday = list[position].idBirthday,
                        namePerson = list[position].namePerson,
                        date = list[position].date)
        )

        setHasOptionsMenu(true)

        val eventRecyclerView: RecyclerView = requireView().findViewById(R.id.recycleViewBirthdays)
        val imageView: ImageView = requireView().findViewById(R.id.imageViewBirthdays)

        list = db.birthdayUserDao().getAllBirthday()

        if (list.count() > 0) {
            eventRecyclerView.adapter = BirthdaysCustomRecyclerAdapter(list, this)
            imageView.visibility = View.GONE
        } else {
            imageView.visibility = View.VISIBLE
            eventRecyclerView.visibility = View.GONE

            imageView.setImageResource(R.drawable.ic_baseline_inbox_24)
        }

        toast(getString(R.string.record_removed))
    }
}