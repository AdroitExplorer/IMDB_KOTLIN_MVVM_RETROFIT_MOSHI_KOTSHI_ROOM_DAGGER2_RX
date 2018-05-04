package imdb.assignment.umesh0492.di.components

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import imdb.assignment.umesh0492.App
import imdb.assignment.umesh0492.di.builder.ActivityBuilder
import imdb.assignment.umesh0492.di.modules.ApplicationModule
import imdb.assignment.umesh0492.di.modules.NetworkModule
import imdb.assignment.umesh0492.di.modules.StorageModule
import javax.inject.Singleton

@Singleton
@Component(modules =
[AndroidInjectionModule::class,
    ApplicationModule::class,
    NetworkModule::class,
    StorageModule::class,
    ActivityBuilder::class])

interface AppComponent {
    fun inject(app: App)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(app: App): Builder

        fun build(): AppComponent

    }
}
