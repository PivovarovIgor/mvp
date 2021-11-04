package ru.brauer.mvp.model.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = RoomGithubUser::class,
        parentColumns = ["uid"],
        childColumns = ["user_UID"],
        onDelete = ForeignKey.CASCADE
    )],
    tableName = "repositories"
)
data class RoomGithubRepository(
    @PrimaryKey val uid: String,
    val mame: String?,
    @ColumnInfo(name = "full_name") val fullName: String?,
    @ColumnInfo(name = "forks_count") val forksCount: Int?,
    val language: String?,
    @ColumnInfo(name = "HTML_URL") val htmlUrl: String?,
    @ColumnInfo(name = "user_UID") val userUid: String,
)