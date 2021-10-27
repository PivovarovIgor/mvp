package ru.brauer.mvp.ui

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.brauer.mvp.databinding.ActivityConvertImageBinding
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class ConvertImageActivity : AppCompatActivity() {

    companion object {
        private const val FILE_NAME_JPEG = "nebel.jpg"
        private const val FILE_NAME_PNG = "nebel.png"
    }

    private val binding: ActivityConvertImageBinding by lazy {
        ActivityConvertImageBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.convertPicture.setOnClickListener {
            filePictureToPngFromGpeg()
        }
    }

    private fun filePictureToPngFromGpeg() {

        val fileSource = File(filesDir, FILE_NAME_JPEG)


        val fileDest = File(filesDir, FILE_NAME_PNG)

        Single.create<Bitmap> {
            if (!fileSource.exists()) {
                it.onError(IOException("not found file to convert: ${fileSource.canonicalPath}"))
            } else if (!fileSource.canRead()) {
                it.onError(IOException("file '${fileSource.canonicalPath}' is not readable"))
            }
            it.onSuccess(BitmapFactory.decodeFile(fileSource.canonicalPath))
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess { bmp ->
                binding.picture.setImageBitmap(bmp)
            }
            .doOnError {
                Toast.makeText(
                    applicationContext,
                    it.message,
                    Toast.LENGTH_LONG
                ).show()
            }
            .observeOn(Schedulers.io())
            .flatMap { bmp ->
                if (fileDest.exists()) {
                    fileDest.delete()
                }
                FileOutputStream(fileDest).let {
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, it)
                    it.close()
                }
                Single.just(fileDest)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Toast.makeText(
                    applicationContext,
                    "converting is success to file ${it.canonicalPath}",
                    Toast.LENGTH_LONG
                )
                    .show()
            }, {
                Toast.makeText(
                    applicationContext,
                    it.message,
                    Toast.LENGTH_LONG
                ).show()
            })
    }
}