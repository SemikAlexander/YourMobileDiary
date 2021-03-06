package com.example.mobilediary

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.mobilediary.database.AppDatabase
import com.example.mobilediary.database.Birthday
import com.example.mobilediary.database.Event
import com.example.mobilediary.database.Holiday
import com.example.mobilediary.databinding.ActivityAddBinding
import java.text.SimpleDateFormat
import java.util.*

class AddActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddBinding

    private val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.US)
    private var dateAndTime = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        val pref = getSharedPreferences("setting", Context.MODE_PRIVATE)
        if (pref.getString("mode", null).toString() == "dark")
            setTheme(R.style.Theme_MobileDiaryNight)
        else
            setTheme(R.style.Theme_MobileDiary)

        pref.edit()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        pref.getString("language", null)?.apply {
            setLocale(this@AddActivity, this)
        }

        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val map = mapOf(getString(R.string.item_events) to "event",
                getString(R.string.item_holidays) to "holiday",
                getString(R.string.item_birthdays) to "birthday")

        val values = map.values.toList()
        val keys = map.keys

        binding.apply {
            setSupportActionBar(toolbarEventActivity)

            eventSpinner.adapter =
                    ArrayAdapter(this@AddActivity, R.layout.spinner_item, keys.toMutableList())
            eventSpinner.setSelection(0)

            eventSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {}

                override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                ) {
                    when {
                        values[position] == "birthday" -> {
                            descriptionEditText.visibility = View.GONE
                            titleEditText.hint = getString(R.string.name_person_hint)
                        }
                        else -> {
                            descriptionEditText.visibility = View.VISIBLE
                            titleEditText.hint = getString(R.string.title_hint)
                        }
                    }
                }
            }

            dateTextView.text = sdf.format(Date())

            val d = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                dateAndTime[Calendar.YEAR] = year
                dateAndTime[Calendar.MONTH] = monthOfYear
                dateAndTime[Calendar.DAY_OF_MONTH] = dayOfMonth

                dateTextView.text = sdf.format(dateAndTime.time)
            }

            dateTextView.setOnClickListener {
                DatePickerDialog(
                        this@AddActivity, d,
                        dateAndTime.get(Calendar.YEAR),
                        dateAndTime.get(Calendar.MONTH),
                        dateAndTime.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add_activity, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_add -> {
                binding.apply {
                    val db = AppDatabase(this@AddActivity)

                    val dateToUnix =
                            SimpleDateFormat("dd.MM.yyyy").parse(dateTextView.text.toString())

                    val unixtime = dateToUnix.time / 1000

                    when (eventSpinner.selectedItemPosition) {
                        0 -> {
                            db.eventUserDao().insertEvent(
                                    Event(
                                            title = if (titleEditText.text.toString().trim().isNotEmpty())
                                                titleEditText.text.toString()
                                            else getString(R.string.empty_title),
                                            description = if (descriptionEditText.text.toString().trim().isNotEmpty())
                                                descriptionEditText.text.toString()
                                            else getString(R.string.empty_description),
                                            date = unixtime
                                    )
                            )
                        }
                        1 -> {
                            db.holidayUserDao().insertHoliday(
                                    Holiday(
                                            title = if (titleEditText.text.toString().trim().isNotEmpty())
                                                titleEditText.text.toString()
                                            else getString(R.string.empty_title),
                                            description = if (descriptionEditText.text.toString().trim().isNotEmpty())
                                                descriptionEditText.text.toString()
                                            else getString(R.string.empty_description),
                                            date = unixtime
                                    )
                            )
                        }
                        2 -> {
                            db.birthdayUserDao().insertBirthday(
                                    Birthday(
                                            namePerson = if (titleEditText.text.toString().trim().isNotEmpty())
                                                titleEditText.text.toString()
                                            else getString(R.string.empty_title),
                                            date = unixtime
                                    )
                            )
                        }
                    }
                    titleEditText.text.clear()
                    descriptionEditText.text.clear()
                    dateTextView.text = sdf.format(Date())

                    toast(getString(R.string.record_added))
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setLocale(context: Context, locale: String) {
        context.resources.configuration.locale = Locale(locale)
        context.resources.updateConfiguration(
                context.resources.configuration,
                context.resources.displayMetrics
        )
    }
}