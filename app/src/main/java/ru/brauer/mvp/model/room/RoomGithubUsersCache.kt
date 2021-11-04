package ru.brauer.mvp.model.room

import ru.brauer.mvp.model.githubusers.GithubUser

class RoomGithubUsersCache(private val db: AppDataBase) : IRoomGithubUsersCache {
    override fun save(users: List<GithubUser>) {
        val roomUsers = users.map { user ->
            RoomGithubUser(
                user.id ?: "",
                user.login ?: "",
                user.avatarUrl ?: "",
                user.reposUrl ?: ""
            )
        }
        db.userDao.insert(roomUsers)
    }

    override fun get(): List<GithubUser> =
        db.userDao.getAll().map { roomUser ->
            GithubUser(
                roomUser.uid,
                roomUser.login,
                roomUser.avatarUrl,
                roomUser.reposUrl
            )
        }
}