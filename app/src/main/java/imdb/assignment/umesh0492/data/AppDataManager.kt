package imdb.assignment.umesh0492.data

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.content.Context
import android.support.annotation.Nullable
import imdb.assignment.umesh0492.data.local.db.DbHelper
import imdb.assignment.umesh0492.data.local.pref.PreferencesHelper
import imdb.assignment.umesh0492.data.model.MoviesDataClass
import imdb.assignment.umesh0492.data.model.SearchDataClass
import imdb.assignment.umesh0492.data.remote.ApiHelper
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AppDataManager
@Inject constructor(context: Context, dbHelper: DbHelper, preferencesHelper: PreferencesHelper, apiHelper: ApiHelper) : DataManager {

    private var mApiHelper: ApiHelper = apiHelper
    private var mContext: Context = context
    private var mDbHelper: DbHelper = dbHelper
    private var mPreferencesHelper: PreferencesHelper = preferencesHelper

    private var compositeDisposable = CompositeDisposable()

    override var currentUserName: String
        get() = mPreferencesHelper.currentUserName
        set(value) {
            mPreferencesHelper.currentUserName = value
        }

    override fun getMovieListApiCall(pageNo: Int): Single<SearchDataClass> {

        compositeDisposable.add(mApiHelper.getMovieListApiCall(pageNo)
                .subscribeOn(Schedulers.computation())
                .subscribe({ movieResponse ->
                    for (movie in movieResponse.Search) {
                        insertMovie(movie)
                    }
                },
                        {
                            //view.error(throwable)
                        }))

        return mApiHelper.getMovieListApiCall(pageNo)
    }

    override fun getMovieDetailsApiCall(id: String): Single<MoviesDataClass> {
        compositeDisposable.add(mApiHelper.getMovieDetailsApiCall(id)
                .subscribeOn(Schedulers.computation())
                .subscribe({ movieResponse ->
                    updateMovie(movieResponse)
                },
                        {
                            //view.error(throwable)
                        }))
        return mApiHelper.getMovieDetailsApiCall(id)
    }


    override fun insertMovie(movie: MoviesDataClass) {
        mDbHelper.insertMovie(movie)
    }

    override fun updateMovie(movie: MoviesDataClass) {
        mDbHelper.updateMovie(movie)
    }

    override fun getMovieById(id: String): MoviesDataClass {
        return mDbHelper.getMovieById(id)
    }

    override fun getMovieListFromDB(): LiveData<List<MoviesDataClass>> {
        return mDbHelper.getMovieListFromDB()
    }
}
