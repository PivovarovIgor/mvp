package ru.brauer.mvp.model.orm

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "login") val login: String?,
    @ColumnInfo(name = "avatar_url") val avatarUrl: String?
)