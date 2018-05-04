package imdb.assignment.umesh0492.di.modules

import android.arch.persistence.room.Room
import android.content.Context
import dagger.Module
import dagger.Provides
import imdb.assignment.umesh0492.data.local.db.AppDBHelper
import imdb.assignment.umesh0492.data.local.db.AppDatabase
import imdb.assignment.umesh0492.data.local.db.DbHelper
import imdb.assignment.umesh0492.data.local.pref.AppPreferences
import imdb.assignment.umesh0492.data.local.pref.PreferencesHelper
import imdb.assignment.umesh0492.di.ConfigConst
import imdb.assignment.umesh0492.di.qualifiers.DatabaseInfo
import imdb.assignment.umesh0492.di.qualifiers.PreferenceInfo
import javax.inject.Singleton

@Module
class StorageModule{

    @Provides
    @DatabaseInfo
    fun provideDatabaseName(): String {
        return ConfigConst.DB_NAME
    }
    @Provides
    @Singleton
    fun provideAppDatabase(@DatabaseInfo dbName: String, context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, dbName).fallbackToDestructiveMigration()
                .build()
    }

    @Provides
    @Singleton
    fun provideDbHelper(appDbHelper: AppDBHelper): DbHelper {
        return appDbHelper
    }

    @Provides
    @PreferenceInfo
    fun providePreferenceName(): String {
        return ConfigConst.PREF_NAME
    }

    @Provides
    @Singleton
    fun providePreferencesHelper(appPreferencesHelper: AppPreferences): PreferencesHelper {
        return appPreferencesHelper
    }
}
