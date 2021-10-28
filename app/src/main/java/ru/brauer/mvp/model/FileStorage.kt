package ru.brauer.mvp.model

import android.app.Application
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.brauer.mvp.presenter.IFileStorage
import java.io.File
import java.io.IOException

class FileStorage(
    private val context: Application
) : IFileStorage {

    override fun readFile(fileName: String): Single<ByteArray> =
        Single.create<ByteArray> {
            val file = File(context.filesDir, fileName)
            if (!file.exists()) {
                it.onError(IOException("not found file to convert: ${file.canonicalPath}"))
            } else if (!file.canRead()) {
                it.onError(IOException("file '${file.canonicalPath}' is not readable"))
            }
            it.onSuccess(file.readBytes())
        }.subscribeOn(Schedulers.io())

    override fun writeFile(bufferData: ByteArray, fileName: String): Completable =
        Completable.create {
            val file = File(context.filesDir, fileName)
            if (file.exists()) {
                file.delete()
            }
            file.writeBytes(bufferData)
            it.onComplete()
        }.subscribeOn(Schedulers.io())
}

