package ru.brauer.mvp.model.githubusers

import com.google.gson.annotations.Expose

data class GithubRepo(
    @Expose val id: String?,
    @Expose val name: String?,
    @Expose val fullName: String?
)