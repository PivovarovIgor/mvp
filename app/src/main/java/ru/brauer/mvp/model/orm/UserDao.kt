package ru.brauer.mvp.model.orm

import androidx.room.*

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