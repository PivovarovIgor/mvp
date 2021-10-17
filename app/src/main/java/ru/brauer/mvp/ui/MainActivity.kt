package ru.brauer.mvp.ui

import android.os.Bundle
import com.github.terrakok.cicerone.androidx.AppNavigator
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import ru.brauer.mvp.App
import ru.brauer.mvp.R
import ru.brauer.mvp.databinding.ActivityMainBinding
import ru.brauer.mvp.presenter.IBackButtonListener
import ru.brauer.mvp.presenter.IMainView
import ru.brauer.mvp.presenter.ScreenPresenter

class MainActivity : MvpAppCompatActivity(), IMainView {


    private val navigation = AppNavigator(this, R.id.container)

    private val presenter by moxyPresenter {
        ScreenPresenter(
            App.instance.router,
            AndroidScreens()
        )
    }

    private val vb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vb.root)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        App.instance.navigationHolder.setNavigator(navigation)
    }

    override fun onPause() {
        super.onPause()
        App.instance.navigationHolder.removeNavigator()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        supportFragmentManager.fragments.forEach {
            if (it is IBackButtonListener && it.backPressed()) {
                return
            }
        }
        presenter.backClicked()
    }
}