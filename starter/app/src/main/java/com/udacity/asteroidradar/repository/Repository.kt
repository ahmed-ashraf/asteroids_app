package com.udacity.asteroidradar.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.DailyPicture
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.entities.NetworkAsteroidContainer
import com.udacity.asteroidradar.database.entities.asDatabaseModel
import com.udacity.asteroidradar.database.entities.asDomainModel
import com.udacity.asteroidradar.network.AsteroidsApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.util.*

class Repository(private val database: AsteroidDatabase) {

    val asteroids: LiveData<List<Asteroid>> =
        Transformations.map(database.asteroidDao.getAsteroidsStartFromDate(Date())) {
            it.asDomainModel()
        }

    val dailyPicture: LiveData<DailyPicture> =
        Transformations.map(database.asteroidDao.getLastImage()) {
            it?.asDomainModel()
        }

    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO) {
            try {
                val response = AsteroidsApi.retrofitService.getAsteroids(null, null)
                val list: List<Asteroid> =
                    parseAsteroidsJsonResult(JSONObject(response as Map<Any, Any>))
                database.asteroidDao.insertAll(*NetworkAsteroidContainer(list).asDatabaseModel())
            } catch (e: Exception) {}
        }
    }

    suspend fun refreshDailyPicture() {
        withContext(Dispatchers.IO) {
            try {
                val response = AsteroidsApi.retrofitService.getDailyPictureData()
                database.asteroidDao.insertDailyPicture(response.asDatabaseModel())
            } catch (e: Exception) {}
        }
    }
}