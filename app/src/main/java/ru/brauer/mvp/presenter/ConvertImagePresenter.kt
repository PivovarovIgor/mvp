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
                return
            }
        }
        viewState.showState("")
        processingOfConvert = fileStorage.readFile(FILE_NAME_JPEG)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                viewState.showState("start conversion")
                viewState.setStateOfButton(ImageConversionButtonStates.STARTED)
            }
            .doOnSuccess {
                viewState.showState("file reading is complete")
                viewState.loadImageFromFile(it)
                viewState.showState("showed image")
            }
            .flatMap {
                imageConvertor.convertToPng(it)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess {
                viewState.showState("Conversion to PNG completed")
            }
            .flatMapCompletable {
                fileStorage.writeFile(it, FILE_NAME_PNG)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnDispose { viewState.setStateOfButton(ImageConversionButtonStates.STOPED) }
            .doOnError { viewState.setStateOfButton(ImageConversionButtonStates.STOPED) }
            .subscribe({
                viewState.showState("PNG is saved")
                viewState.setStateOfButton(ImageConversionButtonStates.STOPED)
            }, {
                viewState.showAlert(it.message ?: "Unknown error")
                viewState.setStateOfButton(ImageConversionButtonStates.STOPED)
            })
    }
}