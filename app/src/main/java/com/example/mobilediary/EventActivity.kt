package com.example.mobilediary

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.mobilediary.database.AppDatabase
import com.example.mobilediary.databinding.ActivityEventBinding
import java.text.SimpleDateFormat
import java.util.*

class EventActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEventBinding

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
        setContentView(R.layout.activity_event)

        pref.getString("language", null)?.apply {
            setLocale(this@EventActivity, this)
        }

        binding = ActivityEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val map = mapOf(
            getString(R.string.item_events) to "event",
            getString(R.string.item_holidays) to "holiday",
            getString(R.string.item_birthdays) to "birthday"
        )

        val values = map.values.toList()
        val keys = map.keys

        binding.apply {
            val db = AppDatabase(this@EventActivity)

            setSupportActionBar(toolbarEventActivity)

            when (intent.getStringExtra("intent_entity")) {
                "event" -> {
                    eventSpinner.setSelection(0)

                    val event = db.eventUserDao().getEvent(intent.getLongExtra("id_record", 0))
                    titleEditText.setText(event.title)
                    descriptionEditText.setText(event.description)
                    dateTextView.text = sdf.format(Date(event.date * 1000))
                }
                "holiday" -> {
                    eventSpinner.setSelection(1)

                    val holiday = db.holidayUserDao().getHoliday(intent.getLongExtra("id_record", 0))
                    titleEditText.setText(holiday.title)
                    descriptionEditText.setText(holiday.description)
                    dateTextView.text = sdf.format(Date(holiday.date * 1000))
                }
                "birthday" -> {
                    eventSpinner.setSelection(2)

                    val birthday = db.birthdayUserDao().getBirthday(intent.getLongExtra("id_record", 0))
                    titleEditText.setText(birthday.namePerson)
                    dateTextView.text = sdf.format(Date(birthday.date * 1000))
                    descriptionEditText.visibility = View.GONE
                }
            }

            val d = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                dateAndTime[Calendar.YEAR] = year
                dateAndTime[Calendar.MONTH] = monthOfYear
                dateAndTime[Calendar.DAY_OF_MONTH] = dayOfMonth

                dateTextView.text = sdf.format(dateAndTime.time)
            }

            dateTextView.setOnClickListener {
                DatePickerDialog(
                        this@EventActivity, d,
                        dateAndTime.get(Calendar.YEAR),
                        dateAndTime.get(Calendar.MONTH),
                        dateAndTime.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_event_activity, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_update -> {
                binding.apply {
                    val db = AppDatabase(this@EventActivity)

                    val dateToUnix = sdf.parse(dateTextView.text.toString())
                    val unixtime = dateToUnix.time / 1000

                    when (intent.getStringExtra("intent_entity")) {
                        "event" -> {
                            db.eventUserDao().update(
                                    title = titleEditText.text.toString(),
                                    description = descriptionEditText.text.toString(),
                                    date = unixtime
                            )
                        }
                        "holiday" -> {
                            db.holidayUserDao().update(
                                    title = titleEditText.text.toString(),
                                    description = descriptionEditText.text.toString(),
                                    date = unixtime
                            )
                        }
                        "birthday" -> {
                            db.birthdayUserDao().update(
                                    namePerson = titleEditText.text.toString(),
                                    date = unixtime
                            )
                        }
                    }

                    toast(getString(R.string.record_updated))
                }
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