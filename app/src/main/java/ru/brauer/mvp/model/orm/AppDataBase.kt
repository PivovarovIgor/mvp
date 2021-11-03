package ru.brauer.mvp.model.orm

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        RoomGithubUser::class,
        RoomGithubRepository::class
    ],
    version = 1
)
abstract class AppDataBase : RoomDatabase() {
    abstract val userDao: UserDao
    abstract val repositoryDao: RepositoryDao

    companion object {
        private const val DB_NAME = "database.db"
        private var instance: AppDataBase? = null

        fun getDatabase(context: Context?): AppDataBase {
            if (instance == null) {
                instance =
                    Room.databaseBuilder(requireNotNull(context), AppDataBase::class.java, DB_NAME)
                        .fallbackToDestructiveMigration()
                        .build()
            }
            return requireNotNull(instance) { "Database has not been created." }
        }
    }
}