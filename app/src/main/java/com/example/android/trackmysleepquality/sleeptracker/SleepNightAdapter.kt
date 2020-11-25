package com.example.android.trackmysleepquality.sleeptracker

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android.trackmysleepquality.database.SleepNight

class SleepNightAdapter : RecyclerView.Adapter<TextItemViewHolder>() {
    var data = listOf<SleepNight>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: TextItemViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextItemViewHolder {
        return TextItemViewHolder.from(parent)
    }
}