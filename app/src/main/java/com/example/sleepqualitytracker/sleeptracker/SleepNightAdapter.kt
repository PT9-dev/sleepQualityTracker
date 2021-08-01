package com.example.sleepqualitytracker.sleeptracker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sleepqualitytracker.R
import com.example.sleepqualitytracker.database.SleepNight
import com.example.sleepqualitytracker.databinding.ItemViewBinding

class SleepNightAdapter: ListAdapter<SleepNight, SleepNightAdapter.ViewHolder>(SleepNightDiffCallBack()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)

    }


    class ViewHolder private constructor(private val binding: ItemViewBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(
            item: SleepNight
        ) {
            binding.sleep = item
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view: ItemViewBinding = DataBindingUtil.inflate(layoutInflater, R.layout.item_view, parent, false)
                return ViewHolder(view)
            }
        }

    }


}


class SleepNightDiffCallBack: DiffUtil.ItemCallback<SleepNight>(){
    override fun areItemsTheSame(oldItem: SleepNight, newItem: SleepNight): Boolean {
        return oldItem.nightId == newItem.nightId
    }

    override fun areContentsTheSame(oldItem: SleepNight, newItem: SleepNight): Boolean {
        return oldItem == newItem
    }
}
