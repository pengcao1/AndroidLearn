/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.trackmysleepquality.sleeptracker

import android.app.Application
import androidx.lifecycle.*
import com.example.android.trackmysleepquality.database.SleepDatabaseDao
import com.example.android.trackmysleepquality.database.SleepNight
import com.example.android.trackmysleepquality.formatNights
import kotlinx.coroutines.*

/**
 * ViewModel for SleepTrackerFragment.
 */
class SleepTrackerViewModel(
        val database: SleepDatabaseDao,
        application: Application) : AndroidViewModel(application) {


    private val tonight = MutableLiveData<SleepNight>()
    val nights = database.getAllNights()

    private val _navigateToSleepQuality = MutableLiveData<SleepNight>()
    val navigateTOSleepQuality: LiveData<SleepNight> get() = _navigateToSleepQuality

    private val _navigateToSleepDetails = MutableLiveData<Long>()
    val navigateToSleepDetails get() = _navigateToSleepDetails


    val startButtonVisible = Transformations.map(tonight) {
        it == null
    }
    val stopButtonVisible = Transformations.map(tonight) {
        it != null
    }
    val clearButtonVisible = Transformations.map(nights) {
        it?.isNotEmpty()
    }

    private val _shouldShowSnackBar = MutableLiveData<Boolean>()
    val shouldShowSnackBar: LiveData<Boolean> get() = _shouldShowSnackBar

    fun downShowSnackBar() {
        _shouldShowSnackBar.value = false
    }

    fun doneNavigating() {
        _navigateToSleepQuality.value = null
    }


    init {
        initializeTonight()
    }

    private fun initializeTonight() {
        viewModelScope.launch {
            tonight.value = getTonightFromDatabase()
        }
    }

    private suspend fun getTonightFromDatabase(): SleepNight? {
        return withContext(Dispatchers.IO) {
            var night = database.getTonight()
            if (night?.endTimeMilli != night?.startTimeMilli) {
                night = null
            }
            night
        }
    }

    fun onStartTracking() {
        viewModelScope.launch {
            var newNight = SleepNight()
            insert(newNight)
            tonight.value = getTonightFromDatabase()
        }
    }

    private suspend fun insert(newNight: SleepNight) {
        withContext(Dispatchers.IO) {
            database.insert(newNight)
        }
    }


    fun onStopTracking() {
        viewModelScope.launch {
            /*
            * In Kotlin, the return@label syntax is used for specifying which function among several
            * nested ones this statement return from, in this case we are specifying to return from launch(),
            * not the lambda
            * */
            val oldNight = tonight.value ?: return@launch
            oldNight.endTimeMilli = System.currentTimeMillis()
            update(oldNight)
            _navigateToSleepQuality.value = oldNight
        }
    }

    private suspend fun update(oldNight: SleepNight) {
        withContext(Dispatchers.IO) {
            database.update(oldNight)
        }
    }

    fun onClear() {
        viewModelScope.launch {
            clear()
            tonight.value = null
        }
        _shouldShowSnackBar.value = true
    }

    private suspend fun clear() {
        withContext(Dispatchers.IO) {
            database.clear()
        }
    }

    fun onSleepNightClicked(id: Long) {
        _navigateToSleepDetails.value = id
    }

    fun onSleepDetailsNavigated() {
        _navigateToSleepDetails.value = null
    }
}

