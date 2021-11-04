package ru.brauer.mvp.model.room

import androidx.room.*

@Dao
interface RepositoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(repository: RoomGithubRepository)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg repositories: RoomGithubRepository)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(repositories: List<RoomGithubRepository>)

    @Update
    fun update(repository: RoomGithubRepository)

    @Update
    fun update(vararg repositories: RoomGithubRepository)

    @Update
    fun update(repositories: List<RoomGithubRepository>)

    @Delete
    fun delete(repository: RoomGithubRepository)

    @Delete
    fun delete(vararg repositories: RoomGithubRepository)

    @Delete
    fun delete(repositories: List<RoomGithubRepository>)

    @Query("SELECT * FROM repositories")
    fun getAll(): List<RoomGithubRepository>

    @Query("SELECT * FROM repositories WHERE user_UID = :userUid")
    fun findForUser(userUid: String): List<RoomGithubRepository>
}