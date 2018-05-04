package imdb.assignment.umesh0492

import android.app.Activity
import android.app.Application
import com.facebook.stetho.Stetho
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import imdb.assignment.umesh0492.di.components.AppComponent
import imdb.assignment.umesh0492.di.components.DaggerAppComponent
import javax.inject.Inject


class App : Application(), HasActivityInjector {

    @Inject
    lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
         DaggerAppComponent.builder()
                .application(this)
                .build().inject(this)
        initStetho()
    }

    private fun initStetho() {
        if (BuildConfig.DEBUG)
            Stetho.initializeWithDefaults(this)
    }

    override fun activityInjector(): DispatchingAndroidInjector<Activity> {
        return activityDispatchingAndroidInjector
    }
}
