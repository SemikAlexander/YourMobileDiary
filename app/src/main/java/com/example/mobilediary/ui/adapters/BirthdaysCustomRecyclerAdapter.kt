package com.example.mobilediary.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mobilediary.R
import com.example.mobilediary.database.Birthday
import java.text.SimpleDateFormat
import java.util.*

class BirthdaysCustomRecyclerAdapter(private val values: List<Birthday>,
                                     private val listener: OnItemClickListener) :
    RecyclerView.Adapter<BirthdaysCustomRecyclerAdapter.MyViewHolder>() {

    override fun getItemCount() = values.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recycleview_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.largeTextView?.text = values[position].namePerson

        val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.US)
        holder.smallTextView?.text = sdf.format(values[position].date)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var largeTextView: TextView? = null
        var smallTextView: TextView? = null

        init {
            largeTextView = itemView.findViewById(R.id.textViewLarge)
            smallTextView = itemView.findViewById(R.id.textViewSmall)

            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}