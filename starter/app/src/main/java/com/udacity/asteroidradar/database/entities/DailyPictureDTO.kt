package com.udacity.asteroidradar.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.udacity.asteroidradar.DailyPicture

@Entity
class DailyPictureDTO(
    val date: String,
    @PrimaryKey
    val mediaType: String,
    val title: String,
    val url: String,
    val explanation: String
)

fun DailyPictureDTO.asDomainModel(): DailyPicture {
    return DailyPicture(
        this.date, this.mediaType, this.title, this.url, this.explanation
    )
}

fun DailyPicture.asDatabaseModel(): DailyPictureDTO {
    return DailyPictureDTO(
        this.date, this.mediaType, this.title, this.url, this.explanation
    )
}