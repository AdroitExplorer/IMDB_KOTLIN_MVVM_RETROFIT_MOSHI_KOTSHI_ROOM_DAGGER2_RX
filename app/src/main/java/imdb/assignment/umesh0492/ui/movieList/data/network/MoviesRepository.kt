package imdb.assignment.umesh0492.ui.movieList.data.network

import android.arch.lifecycle.LiveData
import android.os.AsyncTask
import imdb.assignment.umesh0492.App
import imdb.assignment.umesh0492.data.MoviesDatabase
import imdb.assignment.umesh0492.ui.movieList.data.database.MoviesDAO
import imdb.assignment.umesh0492.ui.movieList.data.model.MoviesDataClass
import imdb.assignment.umesh0492.ui.movieList.data.model.SearchDataClass
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviesRepository() {

    private var mMoviesDao: MoviesDAO
    private var movieList: LiveData<List<MoviesDataClass>>

    fun getMovieList(callback: Callback<SearchDataClass>, pageNo: Int) {
        val service = App.instance.retrofit.create(MoviesService::class.java)
        val movies: Call<SearchDataClass> = service.getMovieList("an", pageNo = pageNo.toString())
        movies.enqueue(object : Callback<SearchDataClass> {

            override fun onResponse(call: Call<SearchDataClass>?, response: Response<SearchDataClass>?) {
                if (response?.body() != null && response.body()!!.Search.isNotEmpty()) {
                    for (movie in response.body()!!.Search) {
                        insert(movie)
                    }
                    callback.onResponse(call, response)
                }

            }

            override fun onFailure(call: Call<SearchDataClass>?, t: Throwable?) {
                callback.onFailure(call, t)
            }

        })
    }

    fun getMovieList(): LiveData<List<MoviesDataClass>> {
        return movieList
    }

    init {
        val db = MoviesDatabase.getDatabase(App.instance)
        mMoviesDao = db!!.getMoviesDAO()
        movieList = mMoviesDao.allMovies
    }

    private class InsertAsyncTask internal constructor(private val mAsyncTaskDao: MoviesDAO) : AsyncTask<MoviesDataClass, Void, Void>() {

        override fun doInBackground(vararg params: MoviesDataClass): Void? {
            mAsyncTaskDao.insert(params[0])
            return null
        }
    }

    fun insert(movie: MoviesDataClass) {
        InsertAsyncTask(mMoviesDao).execute(movie)
    }
}
