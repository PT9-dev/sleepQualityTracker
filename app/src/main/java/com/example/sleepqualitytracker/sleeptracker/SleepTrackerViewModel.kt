package com.example.sleepqualitytracker.sleeptracker

import android.app.Application
import android.text.Spanned
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.sleepqualitytracker.database.SleepDatabaseDao
import com.example.sleepqualitytracker.database.SleepNight
import kotlinx.coroutines.*

class SleepTrackerViewModel(
    val database: SleepDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    private var viewJob = Job()
    private val _tonight = MutableLiveData<SleepNight?>()
    private val tonight: LiveData<SleepNight?>
        get() = _tonight
    private var _navigate = MutableLiveData<SleepNight>()
    val navigate: LiveData<SleepNight>
        get() = _navigate

    private var _snackBar = MutableLiveData<Boolean>()
    val snackbar: LiveData<Boolean>
        get() = _snackBar


    private val resources = application.resources

    private val allNights = database.getAllNight()
    val nights: LiveData<List<SleepNight>>
        get() = allNights


    val startVisible: LiveData<Boolean> = Transformations.map(tonight) {
        null == it
    }

    val stopVisible: LiveData<Boolean> = Transformations.map(tonight) {
        null != it
    }

    val clearVisible: LiveData<Boolean> = Transformations.map(allNights) {
        it.isNotEmpty()

    }


    private var uiscope = CoroutineScope(Dispatchers.Main + viewJob)

    init {
        initializeToNight()
        _navigate.value = null
    }

    private fun initializeToNight() {
        uiscope.launch {
            _tonight.value = getToNightFromDatabase()

        }
    }


    fun onStartTracking() {
        uiscope.launch {
            insert(SleepNight())
            _tonight.value = getToNightFromDatabase()
        }
    }

    fun onStopTracking() {
        uiscope.launch {
            val oldNight = _tonight.value ?: return@launch
            oldNight.endTimeMilli = System.currentTimeMillis()
            update(oldNight)
            _tonight.value = getToNightFromDatabase()
            _navigate.value = oldNight
            doneNavigating()
        }


    }

    fun onClear() {
        uiscope.launch {
            clear()
            _tonight.value = null
            _snackBar.postValue(true)
        }
    }

    private suspend fun getToNightFromDatabase(): SleepNight? {
        return withContext(Dispatchers.IO) {
            var night = database.getToNight()

            if (night?.endTimeMilli != night?.startTimeMilli)
                night = null
            night
        }

    }

    private suspend fun update(sleepNight: SleepNight) {
        withContext(Dispatchers.IO) {
            database.update(sleepNight)
        }

    }

    private suspend fun insert(sleepNight: SleepNight) {
        withContext(Dispatchers.IO) {
            database.insert(sleepNight)
        }

    }

    suspend fun clear() {
        withContext(Dispatchers.IO) {
            database.clear()
        }
    }

    fun doneNavigating() {
        _navigate.value = null
    }

    fun doneShowingSnackBar() {
        _snackBar.postValue(false)
    }

    override fun onCleared() {
        super.onCleared()
        viewJob.cancel()
    }


}