package imdb.assignment.umesh0492.data.local.db

import android.arch.lifecycle.LiveData
import imdb.assignment.umesh0492.data.model.MoviesDataClass
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AppDBHelper
@Inject
constructor(private val mAppDatabase: AppDatabase) : DbHelper {

    override fun insertMovie(movie: MoviesDataClass) {
        mAppDatabase.moviesDAO().insert(movie)
    }

    override fun updateMovie(movie: MoviesDataClass) {
        mAppDatabase.moviesDAO().update(movie)
    }

    override fun getMovieById(id: String) : MoviesDataClass{
        return mAppDatabase.moviesDAO().getMovieById(id)
    }

    override fun getMovieListFromDB(): LiveData<List<MoviesDataClass>> {
        return mAppDatabase.moviesDAO().allMovies
    }
}
