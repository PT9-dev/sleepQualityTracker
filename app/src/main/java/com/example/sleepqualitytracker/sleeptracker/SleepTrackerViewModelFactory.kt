package com.example.sleepqualitytracker.sleeptracker

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sleepqualitytracker.database.SleepDatabase
import com.example.sleepqualitytracker.database.SleepDatabaseDao

class SleepTrackerViewModelFactory (
    private val dataSource: SleepDatabaseDao,
    private val application:Application
): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SleepTrackerViewModel::class.java)){
            return SleepTrackerViewModel(dataSource, application) as T
        }

        throw IllegalArgumentException("Invalid Class")
    }
}