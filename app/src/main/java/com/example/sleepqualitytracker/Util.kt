package com.example.sleepqualitytracker

import android.annotation.SuppressLint
import android.content.res.Resources
import android.os.Build
import android.text.Html
import android.text.Spanned
import androidx.core.text.HtmlCompat
import com.example.sleepqualitytracker.database.SleepNight
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

private val ONE_MINUTE_MILLIS = TimeUnit.MILLISECONDS.convert(1, TimeUnit.MINUTES)
private val ONE_HOUR_MILLIS = TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS)

fun convertDurationToFormatted(startTimeMilli: Long, endTimeMilli: Long, res: Resources): String {
    val durationMilli = endTimeMilli - startTimeMilli
    val weekdayString = SimpleDateFormat("EEEE", Locale.getDefault()).format(startTimeMilli)
    return when {
        durationMilli < ONE_MINUTE_MILLIS -> {
            val seconds = TimeUnit.SECONDS.convert(durationMilli, TimeUnit.MILLISECONDS)
            res.getString(R.string.seconds_length, seconds, weekdayString)
        }
        durationMilli < ONE_HOUR_MILLIS -> {
            val minutes = TimeUnit.MINUTES.convert(durationMilli, TimeUnit.MILLISECONDS)
            res.getString(R.string.minutes_length, minutes, weekdayString)
        }
        else -> {
            val hours = TimeUnit.HOURS.convert(durationMilli, TimeUnit.MILLISECONDS)
            res.getString(R.string.hours_length, hours, weekdayString)
        }
    }
}


@SuppressLint("SimpleDateFormat")
fun convertLongToDateString(endTimeMilli: Long): String {
    return SimpleDateFormat("EEEE MMM-dd-yyyy' Time: 'HH:mm")
        .format(endTimeMilli).toString()

}

fun convertNumericQualityToString(sleepQuality: Int, res: Resources): String {
    var qualityString = res.getString(R.string.three_ok)
    when (sleepQuality) {
        -1 -> qualityString = "--"
        0 -> qualityString = res.getString(R.string.zero_very_bad)
        1 -> qualityString = res.getString(R.string.one_poor)
        2 -> qualityString = res.getString(R.string.two_soso)
        4 -> qualityString = res.getString(R.string.four_pretty_good)
        5 -> qualityString = res.getString(R.string.five_excellent)
    }
    return qualityString

}
