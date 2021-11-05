package ru.brauer.mvp.di

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import dagger.Component
import dagger.Module
import dagger.Provides
import ru.brauer.mvp.presenter.IScreens
import ru.brauer.mvp.presenter.ScreenPresenter
import ru.brauer.mvp.presenter.repository.RepositoryPresenter
import ru.brauer.mvp.presenter.user.UserPresenter
import ru.brauer.mvp.presenter.users.UsersPresenter
import ru.brauer.mvp.ui.AndroidScreens
import ru.brauer.mvp.ui.MainActivity
import javax.inject.Singleton

@Module
class CiceroneModule {

    private val cicerone = Cicerone.create()

    @Provides
    fun cicerone(): Cicerone<Router> = cicerone

    @Singleton
    @Provides
    fun navigationHolder(): NavigatorHolder = cicerone.getNavigatorHolder()

    @Singleton
    @Provides
    fun router(): Router = cicerone.router

    @Singleton
    @Provides
    fun screens(): IScreens = AndroidScreens()
}

@Singleton
@Component(
    modules = [
        CiceroneModule::class
    ]
)
interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(screenPresenter: ScreenPresenter)
    fun inject(usersPresenter: UsersPresenter)
    fun inject(userPresenter: UserPresenter)
    fun inject(repositoryPresenter: RepositoryPresenter)
}