package ru.brauer.mvp.di

import androidx.room.Room
import dagger.Module
import dagger.Provides
import ru.brauer.mvp.App
import ru.brauer.mvp.model.room.AppDataBase
import javax.inject.Singleton

@Module
class DatabaseModule {

    companion object {
        private const val DB_NAME = "database.db"
    }

    @Singleton
    @Provides
    fun getDatabase(context: App): AppDataBase =
        Room.databaseBuilder(
            context,
            AppDataBase::class.java,
            DB_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
}