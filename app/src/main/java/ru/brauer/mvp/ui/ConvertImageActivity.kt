package ru.brauer.mvp.ui

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ru.brauer.mvp.databinding.ActivityConvertImageBinding
import java.io.File
import java.io.FileOutputStream

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
        if (!fileSource.exists()) {
            Toast.makeText(
                applicationContext,
                "not found file to convert: ${fileSource.canonicalPath}",
                Toast.LENGTH_LONG
            ).show()
            return
        } else if (!fileSource.canRead()) {
            Toast.makeText(
                applicationContext,
                "file '${fileSource.canonicalPath}' is not readable",
                Toast.LENGTH_LONG
            ).show()
            return
        }

        val fileDest = File(filesDir, FILE_NAME_PNG)
        if (fileDest.exists()) {
            fileDest.delete()
        }

        val bmp = BitmapFactory.decodeFile(fileSource.canonicalPath)
        binding.picture.setImageBitmap(bmp)

        FileOutputStream(fileDest).let {
            try {
                bmp.compress(Bitmap.CompressFormat.PNG, 100, it)
                it.close()
            } catch (e: Exception) {
                Toast.makeText(
                    applicationContext,
                    e.message,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}