package ru.brauer.mvp.presenter

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import moxy.MvpPresenter

class ConvertImagePresenter(
    private val fileStorage: IFileStorage,
    private val imageConvertor: IImageConvertor
) : MvpPresenter<IConvertImageView>() {

    companion object {
        private const val FILE_NAME_JPEG = "nebel.jpg"
        private const val FILE_NAME_PNG = "nebel.png"
    }

    private var processingOfConvert: Disposable? = null

    fun startConvertFile() {
        processingOfConvert?.let {
            if (!it.isDisposed) {
                it.dispose()
            }
        }
        processingOfConvert = fileStorage.readFile(FILE_NAME_JPEG)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess {
                viewState.loadImageFromFile(it)
            }
            .doOnError {
                viewState.showMessage(it.message ?: "Unknown error")
            }
            .flatMap {
                imageConvertor.convertToPng(it)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess {
                viewState.showMessage("Conversion to PNG completed")
            }
            .doOnError {
                viewState.showMessage(it.message ?: "Unknown error of conversion")
            }
            .flatMapCompletable {
                fileStorage.writeFile(it, FILE_NAME_PNG)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {viewState.showMessage("PNG is saved")},
                {viewState.showMessage(it.message ?: "Unknown error of PNG file saving")}
            )
    }
}