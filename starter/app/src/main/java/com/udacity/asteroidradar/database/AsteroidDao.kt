package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.asteroidradar.database.entities.AsteroidDTO
import com.udacity.asteroidradar.database.entities.DailyPictureDTO
import java.util.*

@Dao
interface AsteroidDao {
    @Query("select * from AsteroidDTO ORDER BY closeApproachDate ASC")
    fun getAllAsteroids(): LiveData<List<AsteroidDTO>>

    @Query(
        "select * from AsteroidDTO WHERE closeApproachDate >= :startDate AND closeApproachDate <= :endDate ORDER BY closeApproachDate ASC"
    )
    fun getAsteroidsWithinTimeSpan(startDate: Date, endDate: Date): LiveData<List<AsteroidDTO>>

    @Query(
        "select * from AsteroidDTO WHERE closeApproachDate >= :startDate ORDER BY closeApproachDate ASC"
    )
    fun getAsteroidsStartFromDate(startDate: Date): LiveData<List<AsteroidDTO>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg asteroids: AsteroidDTO)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDailyPicture(picture: DailyPictureDTO)

    @Query(
        "select * from DailyPictureDTO WHERE mediaType == 'image'"
    )
    fun getLastImage() : LiveData<DailyPictureDTO>
}

