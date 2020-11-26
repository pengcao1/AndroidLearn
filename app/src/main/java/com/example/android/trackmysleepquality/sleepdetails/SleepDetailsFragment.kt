package com.example.android.trackmysleepquality.sleepdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.trackmysleepquality.R
import com.example.android.trackmysleepquality.database.SleepDatabase
import com.example.android.trackmysleepquality.databinding.FragmentSleepDetailsBinding

class SleepDetailsFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentSleepDetailsBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_sleep_details, container, false)

        val application = requireNotNull(this.activity).application
        val arguments = SleepDetailsFragmentArgs.fromBundle(arguments!!)
        val dataSource = SleepDatabase.getInstance(application).sleepDatabaseDao
        val viewModelFactory = SleepDetailsViewModelFactory(arguments.sleepNightKey, dataSource)

        val viewModel = ViewModelProvider(this, viewModelFactory).get(SleepDetailsViewModel::class.java)
        binding.sleepdetails = viewModel
        binding.lifecycleOwner = this

        viewModel.navigateToSleepTracker.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                findNavController().navigate(SleepDetailsFragmentDirections.actionSleepDetailsFragmentToSleepTrackerFragment())
                viewModel.doneNavigating()
            }
        })
        return binding.root
    }
}