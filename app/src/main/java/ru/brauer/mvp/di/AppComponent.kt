package ru.brauer.mvp.di

import dagger.Component
import ru.brauer.mvp.presenter.ScreenPresenter
import ru.brauer.mvp.presenter.repository.RepositoryPresenter
import ru.brauer.mvp.presenter.user.UserPresenter
import ru.brauer.mvp.presenter.users.UsersPresenter
import ru.brauer.mvp.ui.MainActivity
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        CiceroneModule::class,
        RepoModule::class,
        ApiModule::class,
        AppModule::class,
        UiScheduler::class,
        NetworkStatusModule::class,
        CacheModule::class,
        DatabaseModule::class
    ]
)
interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(screenPresenter: ScreenPresenter)
    fun inject(usersPresenter: UsersPresenter)
    fun inject(userPresenter: UserPresenter)
    fun inject(repositoryPresenter: RepositoryPresenter)
}