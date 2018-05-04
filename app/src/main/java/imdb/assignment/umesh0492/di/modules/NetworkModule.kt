package imdb.assignment.umesh0492.di.modules

import android.content.Context
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import imdb.assignment.umesh0492.BuildConfig
import imdb.assignment.umesh0492.data.remote.ApiHelper
import imdb.assignment.umesh0492.data.remote.AppApi
import imdb.assignment.umesh0492.util.ApplicationJsonAdapterFactory
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import imdb.assignment.umesh0492.di.qualifiers.ApiInfo



@Module
class NetworkModule{

    @Provides
    @ApiInfo
    fun provideApiKey(): String {
        return BuildConfig.API_KEY
    }

    @Provides
    @Singleton
    fun provideAppApi(appApi: AppApi): ApiHelper {
        return appApi
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
                .add(ApplicationJsonAdapterFactory.INSTANCE)
                .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(moshi: Moshi,
                        rxJava2CallAdapterFactory: RxJava2CallAdapterFactory,
                        okHttpClient: OkHttpClient): Retrofit {
        return  Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(rxJava2CallAdapterFactory)
                .client(okHttpClient)
                .build()
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(cache: Cache): OkHttpClient {
        val client = OkHttpClient.Builder()
                .cache(cache)
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG)
            client.addNetworkInterceptor(StethoInterceptor())

        return client.build()
    }

    @Provides
    @Singleton
    fun providesOkhttpCache(context: Context): Cache {
        val cacheSize = 10 * 1024 * 1024 // 10 MB
        return Cache(context.cacheDir, cacheSize.toLong())
    }

    @Provides
    @Singleton
    fun providesRxJavaCallAdapterFactory(): RxJava2CallAdapterFactory {
        return RxJava2CallAdapterFactory.create()
    }

}
