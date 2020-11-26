package com.example.android.trackmysleepquality.sleeptracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.android.trackmysleepquality.R
import com.example.android.trackmysleepquality.convertDurationToFormatted
import com.example.android.trackmysleepquality.convertNumericQualityToString
import com.example.android.trackmysleepquality.database.SleepNight

class TextItemViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val sleepLength: TextView = itemView.findViewById(R.id.sleep_length)
    private val quality: TextView = itemView.findViewById(R.id.quality_string)
    private val qualityImage: ImageView = itemView.findViewById(R.id.quality_image)


    fun bind(item: SleepNight) {
        val res = itemView.context.resources
        quality.text = convertNumericQualityToString(item.sleepQuality, res)
        sleepLength.text = convertDurationToFormatted(item.startTimeMilli, item.endTimeMilli, res)
        qualityImage.setImageResource(when (item.sleepQuality) {
            0 -> R.drawable.ic_sleep_0
            1 -> R.drawable.ic_sleep_1
            2 -> R.drawable.ic_sleep_2
            3 -> R.drawable.ic_sleep_3
            4 -> R.drawable.ic_sleep_4
            5 -> R.drawable.ic_sleep_5
            else -> R.drawable.ic_sleep_active
        })
    }

    companion object {
        fun from(parent: ViewGroup): TextItemViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.list_item_sleep_night, parent, false)
            return TextItemViewHolder(view)
        }
    }
}

class SleepNightDiffCallBack : DiffUtil.ItemCallback<SleepNight>() {
    override fun areItemsTheSame(oldItem: SleepNight, newItem: SleepNight): Boolean {
        return oldItem.nightId == newItem.nightId
    }

    override fun areContentsTheSame(oldItem: SleepNight, newItem: SleepNight): Boolean {
        return oldItem == newItem
    }

}