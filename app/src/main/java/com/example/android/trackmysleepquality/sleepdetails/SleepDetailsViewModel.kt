package com.example.android.trackmysleepquality.sleepdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.trackmysleepquality.database.SleepDatabaseDao
import com.example.android.trackmysleepquality.database.SleepNight
import java.util.function.BinaryOperator

class SleepDetailsViewModel(private val sleepNightKey: Long = 0L, dataSource: SleepDatabaseDao) : ViewModel() {
    val database = dataSource
    private val night = MediatorLiveData<SleepNight>()
    fun getNight() = night

    private val _navigateToSleepTracker = MutableLiveData<Boolean?>()
    val navigateToSleepTracker: LiveData<Boolean?> get() = _navigateToSleepTracker

    init {
        night.addSource(database.getNightWithId(sleepNightKey), night::setValue)
    }

    fun onClose() {
        _navigateToSleepTracker.value = true
    }

    fun doneNavigating() {
        _navigateToSleepTracker.value = null
    }
}