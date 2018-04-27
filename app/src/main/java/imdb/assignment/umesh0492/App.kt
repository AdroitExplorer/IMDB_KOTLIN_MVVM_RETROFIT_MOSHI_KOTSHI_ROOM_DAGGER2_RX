package imdb.assignment.umesh0492

import android.app.Application
import com.squareup.moshi.Moshi
import imdb.assignment.umesh0492.util.ApplicationJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class App : Application() {

    lateinit var retrofit: Retrofit

    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        retrofit = Retrofit.Builder()
                .baseUrl("http://www.omdbapi.com/?apikey=e22bb521&")
                .addConverterFactory(MoshiConverterFactory.create(createMoshiInstance()))
                .build()
    }

    private fun createMoshiInstance(): Moshi {
        return Moshi.Builder()
                .add(ApplicationJsonAdapterFactory.INSTANCE)
                .build()
    }
}
