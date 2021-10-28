package ru.brauer.mvp.presenter

import io.reactivex.rxjava3.core.Single

interface IImageConvertor {
    fun convertToPng(bufferData: ByteArray): Single<ByteArray>
}
