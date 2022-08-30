package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.*
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.entities.asDomainModel
import com.udacity.asteroidradar.repository.Repository
import kotlinx.coroutines.launch
import java.util.*


class MainViewModel(app: Application) : ViewModel() {

    private val database = AsteroidDatabase.getInstance(app)
    private val repository = Repository(database)

    private val filter = MutableLiveData(AsteroidFilter.ALL)

    val asteroids =
        Transformations.switchMap(filter) {
            when (it) {
                AsteroidFilter.WEEK -> Transformations.map(
                    database.asteroidDao.getAsteroidsWithinTimeSpan(
                        Date(),
                        Date(Date().time + 604800000L)
                    )
                ) {
                    it.asDomainModel()
                }
                AsteroidFilter.TODAY -> Transformations.map(
                    database.asteroidDao.getAsteroidsWithinTimeSpan(Date(), Date())
                ) {
                    it.asDomainModel()
                }
                else -> Transformations.map(
                    database.asteroidDao.getAllAsteroids()
                ) {
                    it.asDomainModel()
                }
            }
        }

    val dailyPicture = repository.dailyPicture

    init {
        viewModelScope.launch {
            repository.refreshAsteroids()
            repository.refreshDailyPicture()
        }
    }

    fun onFilterSelect(filter: AsteroidFilter) {
        this.filter.postValue(filter)
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