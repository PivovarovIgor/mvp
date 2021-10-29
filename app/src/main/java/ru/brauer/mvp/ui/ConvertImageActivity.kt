package ru.brauer.mvp.ui

import android.app.AlertDialog
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Toast
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import ru.brauer.mvp.R
import ru.brauer.mvp.databinding.ActivityConvertImageBinding
import ru.brauer.mvp.model.FileStorage
import ru.brauer.mvp.model.ImageConvertor
import ru.brauer.mvp.presenter.ConvertImagePresenter
import ru.brauer.mvp.presenter.IConvertImageView
import ru.brauer.mvp.presenter.ImageConversionButtonStates

class ConvertImageActivity : MvpAppCompatActivity(), IConvertImageView {

    private val binding: ActivityConvertImageBinding by lazy {
        ActivityConvertImageBinding.inflate(layoutInflater)
    }

    private val presenter: ConvertImagePresenter by moxyPresenter {
        ConvertImagePresenter(
            FileStorage(application),
            ImageConvertor
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.convertPicture.setOnClickListener {
            presenter.startConvertFile()
        }
    }

    override fun loadImageFromFile(bytesOfBmp: ByteArray) {
        BitmapFactory.decodeByteArray(bytesOfBmp, 0, bytesOfBmp.size)
            ?.let { binding.picture.setImageBitmap(it) }
            ?: Toast.makeText(applicationContext, "Error of loading", Toast.LENGTH_LONG).show()
    }

    override fun showAlert(message: String) {
        AlertDialog.Builder(this)
            .setTitle("Error")
            .setMessage(message)
            .create()
            .show()
    }

    override fun showState(message: String) {
        binding.textStates.text = binding.textStates.text.let {
            if (message.isBlank()) {
                ""
            } else {
                "$it\n$message"
            }
        }.trim()
    }

    override fun setStateOfButton(state: ImageConversionButtonStates) {
        if (state == ImageConversionButtonStates.STOPED) {
            R.string.convert_picture_to_png_from_jpeg
        } else {
            R.string.stop_converting
        }.let { binding.convertPicture.text = getString(it) }
    }
}