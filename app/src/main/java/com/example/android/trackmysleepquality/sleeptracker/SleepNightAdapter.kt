package com.example.android.trackmysleepquality.sleeptracker

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.android.trackmysleepquality.database.SleepNight

class SleepNightAdapter : ListAdapter<SleepNight, TextItemViewHolder>(SleepNightDiffCallBack()) {
    override fun onBindViewHolder(holder: TextItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextItemViewHolder {
        return TextItemViewHolder.from(parent)
    }
}