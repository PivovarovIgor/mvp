package ru.brauer.mvp.model.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        RoomGithubUser::class,
        RoomGithubRepository::class
    ],
    version = 2
)
abstract class AppDataBase : RoomDatabase() {
    abstract val userDao: UserDao
    abstract val repositoryDao: RepositoryDao
}