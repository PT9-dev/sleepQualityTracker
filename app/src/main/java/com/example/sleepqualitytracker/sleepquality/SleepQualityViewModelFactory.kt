package com.example.sleepqualitytracker.sleepquality

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sleepqualitytracker.database.SleepDatabaseDao
import java.lang.IllegalArgumentException

class SleepQualityViewModelFactory(
    private val dataSource: SleepDatabaseDao,
    private val nightId: Long
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SleepQualityViewModel::class.java))
            return SleepQualityViewModel(dataSource, nightId) as T

        throw IllegalArgumentException("Unknown Class Encountered")
    }
}
