package com.udacity.asteroidradar.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.udacity.asteroidradar.Asteroid
import java.text.SimpleDateFormat
import java.util.*


data class NetworkAsteroidContainer(val asteroids: List<Asteroid>)

@Entity
data class AsteroidDTO(
    @PrimaryKey
    val id: Long, val codename: String, val closeApproachDate: Date,
    val absoluteMagnitude: Double, val estimatedDiameter: Double,
    val relativeVelocity: Double, val distanceFromEarth: Double,
    val isPotentiallyHazardous: Boolean
)

fun List<AsteroidDTO>.asDomainModel(): List<Asteroid> {
    return map {
        val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        Asteroid(
            it.id,
            it.codename,
            dateFormatter.format(it.closeApproachDate),
            it.absoluteMagnitude,
            it.estimatedDiameter,
            it.relativeVelocity,
            it.distanceFromEarth,
            it.isPotentiallyHazardous
        )
    }
}

fun NetworkAsteroidContainer.asDatabaseModel(): Array<AsteroidDTO> {
    return asteroids.map {
        AsteroidDTO(
            it.id,
            it.codename,
            getDateFromString(it.closeApproachDate),
            it.absoluteMagnitude,
            it.estimatedDiameter,
            it.relativeVelocity,
            it.distanceFromEarth,
            it.isPotentiallyHazardous)
    }.toTypedArray()
}

private fun getDateFromString(date: String): Date {
    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    return formatter.parse(date)
        ?: throw Exception(
            "parse of closeApproachDate $date Exception"
        )
}