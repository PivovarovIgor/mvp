package ru.brauer.mvp.ui

import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Toast
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import ru.brauer.mvp.databinding.ActivityConvertImageBinding
import ru.brauer.mvp.model.FileStorage
import ru.brauer.mvp.model.ImageConvertor
import ru.brauer.mvp.presenter.ConvertImagePresenter
import ru.brauer.mvp.presenter.IConvertImageView

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

    override fun showMessage(message: String) {

    }
}