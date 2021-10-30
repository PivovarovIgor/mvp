package ru.brauer.mvp.model

interface IImageLoader<T> {
    fun loadInto(url: String, container: T)
}