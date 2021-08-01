package com.example.sleepqualitytracker.sleeptracker

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.sleepqualitytracker.R
import com.example.sleepqualitytracker.convertDurationToFormatted
import com.example.sleepqualitytracker.convertNumericQualityToString
import com.example.sleepqualitytracker.database.SleepNight

@BindingAdapter("sleepImage")
fun ImageView.setSleepImage(item: SleepNight) {
    setImageResource(
            when (item.sleepQuality) {
                0 -> R.drawable.sleep_0
                1 -> R.drawable.sleep_1
                2 -> R.drawable.sleep_2
                3 -> R.drawable.sleep_3
                4 -> R.drawable.sleep_4
                5 -> R.drawable.sleep_5
                else -> R.drawable.sleep_tracker_foreground
            }
    )
}

@BindingAdapter("sleepLengthFormatted")
fun TextView.sleepLengthFormatted(item: SleepNight?){
    item?.let {
        text = convertDurationToFormatted(item.startTimeMilli, item.endTimeMilli, context.resources)
    }
}

@BindingAdapter("sleepQualityString")
fun TextView.sleepQualityString(item: SleepNight?){
    item?.let {
        text = convertNumericQualityToString(item.sleepQuality, context.resources)
    }
}

