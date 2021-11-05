package ru.brauer.mvp.ui

import android.os.Bundle
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.androidx.AppNavigator
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import ru.brauer.mvp.App
import ru.brauer.mvp.R
import ru.brauer.mvp.databinding.ActivityMainBinding
import ru.brauer.mvp.presenter.IMainView
import ru.brauer.mvp.presenter.ScreenPresenter
import javax.inject.Inject

class MainActivity : MvpAppCompatActivity(), IMainView {

    private val navigation = AppNavigator(this, R.id.container)

    @Inject lateinit var navigationHolder: NavigatorHolder

    private val presenter by moxyPresenter {
        ScreenPresenter().apply {
            App.instance.appComponent.inject(this)
        }
    }

    private val vb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vb.root)
        App.instance.appComponent.inject(this)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigationHolder.setNavigator(navigation)
    }

    override fun onPause() {
        super.onPause()
        navigationHolder.removeNavigator()
    }

    override fun onBackPressed() {
        supportFragmentManager.fragments.forEach {
            if (it is IBackButtonListener && it.backPressed()) {
                return
            }
        }
        presenter.backClicked()
    }
}