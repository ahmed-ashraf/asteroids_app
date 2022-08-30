package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.entities.asDomainModel
import com.udacity.asteroidradar.repository.Repository
import kotlinx.coroutines.launch
import java.util.*

class MainViewModel(app: Application) : ViewModel() {

    private val database = AsteroidDatabase.getInstance(app)
    private val repository = Repository(database)


    var asteroids = repository.asteroids
    val dailyPicture = repository.dailyPicture

    init {
        viewModelScope.launch {
            repository.refreshAsteroids()
            repository.refreshDailyPicture()
        }
    }

    fun viewWeekAsteroids() {
        asteroids = Transformations.map(
            database.asteroidDao.getAsteroidsWithinTimeSpan(Date(), Date(Date().time + 604800000L))
        ) {
            it.asDomainModel()
        }
    }

    fun viewSavedAsteroids() {
        asteroids = Transformations.map(
            database.asteroidDao.getAllAsteroids()
        ) {
            it.asDomainModel()
        }
    }

    fun viewTodayAsteroids() {
        asteroids = Transformations.map(
            database.asteroidDao.getAsteroidsWithinTimeSpan(Date(), Date())
        ) {
            it.asDomainModel()
        }
    }


    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}