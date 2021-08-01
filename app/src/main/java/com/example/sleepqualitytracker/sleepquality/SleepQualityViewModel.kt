package com.example.sleepqualitytracker.sleepquality

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sleepqualitytracker.database.SleepDatabaseDao
import kotlinx.coroutines.*

class SleepQualityViewModel(
    var database: SleepDatabaseDao,
    var nightId: Long
) : ViewModel() {
    private val _doneNavaigating = MutableLiveData<Boolean>()
    val doneNavigating: LiveData<Boolean>
        get() =_doneNavaigating

    private val viewJob = Job()
    private val uiscope = CoroutineScope(Dispatchers.Main + viewJob)

    init {
        _doneNavaigating.value = false
    }

    fun selectQualityTracker(quality: Int) {
        uiscope.launch {
            updateQuality(quality)
            _doneNavaigating.value = true
        }

    }



    private suspend fun updateQuality(quality: Int) {
        withContext(Dispatchers.IO) {
            val night = database.get(nightId)
            if (night != null) {
                night.sleepQuality = quality
                database.update(night)
            }
        }

    }

}
