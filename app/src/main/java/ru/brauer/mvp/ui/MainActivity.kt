package ru.brauer.mvp.ui

import android.os.Bundle
import android.view.View
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import ru.brauer.mvp.R
import ru.brauer.mvp.databinding.ActivityMainBinding
import ru.brauer.mvp.presenter.CounterId
import ru.brauer.mvp.presenter.MainPresenter
import ru.brauer.mvp.presenter.MainView

class MainActivity : MvpAppCompatActivity(), MainView {

    private val vb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val presenter: MainPresenter by moxyPresenter { MainPresenter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vb.root)

        val listener = View.OnClickListener {
            val id = when (it.id) {
                R.id.btn_counter1 -> CounterId.FIRST
                R.id.btn_counter2 -> CounterId.SECOND
                R.id.btn_counter3 -> CounterId.THIRD
                else -> throw IllegalArgumentException("Undefined id")
            }
            presenter.counterClick(id)
        }
        vb.apply {
            btnCounter1.setOnClickListener(listener)
            btnCounter2.setOnClickListener(listener)
            btnCounter3.setOnClickListener(listener)
        }
    }

    override fun setButton1Text(text: String) {
        vb.btnCounter1.text = text
    }

    override fun setButton2Text(text: String) {
        vb.btnCounter2.text = text
    }

    override fun setButton3Text(text: String) {
        vb.btnCounter3.text = text
    }
}