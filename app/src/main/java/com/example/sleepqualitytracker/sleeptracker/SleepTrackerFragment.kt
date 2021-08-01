package com.example.sleepqualitytracker.sleeptracker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.sleepqualitytracker.R
import com.example.sleepqualitytracker.database.SleepDatabase
import com.example.sleepqualitytracker.databinding.FragmentSleepTrackerBinding
import com.google.android.material.snackbar.Snackbar

class SleepTrackerFragment : Fragment() {
    private lateinit var binding: FragmentSleepTrackerBinding
    private lateinit var viewFactory: SleepTrackerViewModelFactory
    private lateinit var viewModel: SleepTrackerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_sleep_tracker, container, false)
        binding.lifecycleOwner = this

        //val app = requireNotNull(this.activity).application
        val app = requireActivity().application
        val dataSource = SleepDatabase.getInstance(requireContext()).sleepDatabaseDao

        viewFactory = SleepTrackerViewModelFactory(dataSource, app)
        viewModel = ViewModelProvider(this, viewFactory).get(SleepTrackerViewModel::class.java)
        binding.sleepTrackerView = viewModel

        val adapter = SleepNightAdapter()
        binding.sleepList.adapter = adapter
        viewModel.nights.observe(viewLifecycleOwner, {nights ->
            nights.let {
                adapter.submitList(it)
            }
        })


        viewModel.navigate.observe(viewLifecycleOwner, Observer {
            it?.let {
                val action =
                    SleepTrackerFragmentDirections.actionSleepTrackerFragmentToSleepQualityFragment(
                        it.nightId
                    )
                findNavController().navigate(action)
            }
        })

        viewModel.snackbar.observe(viewLifecycleOwner, {
            if (it) {
                displaySnackBar()
                viewModel.doneShowingSnackBar()
            }
        })

        return binding.root
    }

    private fun displaySnackBar() {
        Snackbar.make(
            requireActivity().findViewById(android.R.id.content),
            getString(R.string.cleared_message),
            Snackbar.LENGTH_SHORT // How long to display the message.
        ).show()
    }

}