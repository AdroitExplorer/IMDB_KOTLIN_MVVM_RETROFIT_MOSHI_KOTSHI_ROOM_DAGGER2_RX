package imdb.assignment.umesh0492.data.local.db

import android.arch.lifecycle.LiveData
import imdb.assignment.umesh0492.data.model.MoviesDataClass
import io.reactivex.Observable


interface DbHelper {

    fun getMovieListFromDB(): LiveData<List<MoviesDataClass>>

    fun insertMovie(movie : MoviesDataClass)

    fun updateMovie(movie: MoviesDataClass)

    fun getMovieById(id: String): MoviesDataClass
}
