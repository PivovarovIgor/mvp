package ru.brauer.mvp.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.parcelize.Parcelize

@Parcelize
data class GithubUser(
    @Expose val id: String?,
    @Expose val login: String?,
    @Expose val avatarUrl: String?
) : Parcelable