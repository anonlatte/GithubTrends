package com.anonlatte.trends.db.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "repositories", indices = [Index(value = ["url"], unique = true)])
data class Repository(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    var sinceType: String? = Since.DAILY.value,
    var author: String?,
    var name: String?,
    var avatar: String?,
    var url: String?,
    var description: String?,
    var language: String?,
    var languageColor: String?,
    var stars: Int? = 0,
    var forks: Int? = 0,
    var currentPeriodStars: Int? = 0,
) {
    var indexInResponse: Int = -1
}
