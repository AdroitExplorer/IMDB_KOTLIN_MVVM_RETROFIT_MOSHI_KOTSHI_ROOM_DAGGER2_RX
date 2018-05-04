package imdb.assignment.umesh0492.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import imdb.assignment.umesh0492.App
import imdb.assignment.umesh0492.data.AppDataManager
import imdb.assignment.umesh0492.data.DataManager
import imdb.assignment.umesh0492.util.rx.AppSchedulerProvider
import imdb.assignment.umesh0492.util.rx.SchedulerProvider
import javax.inject.Singleton

@Module
class ApplicationModule{

    @Provides
    @Singleton
    fun provideContext(application: App): Context {
        return application
    }

    @Provides
    @Singleton
    fun provideDataManager(appDataManager: AppDataManager): DataManager {
        return appDataManager
    }

    @Provides
    fun provideSchedulerProvider(): SchedulerProvider {
        return AppSchedulerProvider()
    }
}
