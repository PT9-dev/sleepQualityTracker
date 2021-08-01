package com.example.sleepqualitytracker.sleepquality

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.sleepqualitytracker.R
import com.example.sleepqualitytracker.database.SleepDatabase
import com.example.sleepqualitytracker.databinding.FragmentSleepQualityBinding


class SleepQualityFragment : Fragment() {
    private lateinit var binding:FragmentSleepQualityBinding
    private lateinit var viewModel : SleepQualityViewModel
    private lateinit var viewFactory: SleepQualityViewModelFactory


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sleep_quality, container, false)
        binding.lifecycleOwner = this
        val dataSource = SleepDatabase.getInstance(requireContext()).sleepDatabaseDao
        val args: SleepQualityFragmentArgs by navArgs()

        viewFactory = SleepQualityViewModelFactory(dataSource, args.nightId)
        viewModel = ViewModelProvider(this, viewFactory).get(SleepQualityViewModel::class.java)
        binding.sleepQualityView = viewModel

        viewModel.doneNavigating.observe(viewLifecycleOwner, Observer {
            if(it){
                val action = SleepQualityFragmentDirections.actionSleepQualityFragmentToSleepTrackerFragment()
                findNavController().navigate(action)
            }
        })


        return binding.root
    }

}