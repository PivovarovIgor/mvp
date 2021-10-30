package ru.brauer.mvp.presenter.convertimagefile

import io.reactivex.rxjava3.core.Single

interface IImageConvertor {
    fun convertToPng(bufferData: ByteArray): Single<ByteArray>
}
