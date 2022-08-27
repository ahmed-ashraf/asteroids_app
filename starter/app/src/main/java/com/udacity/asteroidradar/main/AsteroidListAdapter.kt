package com.udacity.asteroidradar.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.databinding.AsteroidListItemBinding

class AsteroidListAdapter(
    private var asteroidList: List<Asteroid>,
    val clickListener: AsteroidListener
) : RecyclerView.Adapter<AsteroidListAdapter.AsteroidListViewHolder>() {

    private lateinit var binding: AsteroidListItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidListViewHolder {
        binding = AsteroidListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AsteroidListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AsteroidListViewHolder, position: Int) {
        val largeNews = asteroidList[position]
        holder.bind(largeNews, clickListener)
    }

    override fun getItemCount(): Int = asteroidList.count()

    fun setList(asteroids: List<Asteroid>?) {
        if (asteroids != null) {
            this.asteroidList = asteroids
            notifyDataSetChanged()
        }
    }

    class AsteroidListener(val clickListener: (asteroid: Asteroid) -> Unit) {
        fun onClick(asteroid: Asteroid) = clickListener(asteroid)
    }

    class AsteroidListViewHolder(private var binding: AsteroidListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(asteroid: Asteroid, clickListener: AsteroidListener) {
            binding.asteroid = asteroid
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

//        companion object {
//            /** Get [AsteroidListViewHolder] */
//            fun from(parent: ViewGroup): AsteroidListViewHolder {
//                val layoutInflater = LayoutInflater.from(parent.context)
//                val binding = AsteroidListItemBinding.inflate(layoutInflater, parent, false)
//
//                return AsteroidListViewHolder(binding)
//            }
//        }

    }


}