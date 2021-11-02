package ru.brauer.mvp.model.orm

import androidx.room.*

@Entity
data class User(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "login") val login: String?,
    @ColumnInfo(name = "avatar_url") val avatarUrl: String?
)

@Dao
interface UserDao {

    @Query("SELECT * FROM User")
    fun getAll(): List<User>

    @Query("SELECT * FROM User WHERE uid IN (:usersIds)")
    fun getByIds(usersIds: IntArray): List<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAll(users: List<User>)

    @Delete
    fun delete(user: User)
}

@Database(
    entities = [
        User::class
    ],
    version = 1
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun userDao(): UserDao
}