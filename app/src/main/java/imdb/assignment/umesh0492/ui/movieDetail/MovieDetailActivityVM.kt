package imdb.assignment.umesh0492.ui.movieDetail

import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import imdb.assignment.umesh0492.ui.movieDetail.network.MoviesDetailRepository
import imdb.assignment.umesh0492.ui.movieList.data.model.MoviesDataClass
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieDetailActivityVM(movieIncomplete: MoviesDataClass) {

    var movie: ObservableField<MoviesDataClass> = ObservableField(movieIncomplete)
    private val repo: MoviesDetailRepository = MoviesDetailRepository()
    var isLoading: ObservableBoolean = ObservableBoolean(false)

    fun getMovie(imdbID: String) {
        isLoading.set(true)
        repo.getMovie(object : Callback<MoviesDataClass> {
            override fun onResponse(call: Call<MoviesDataClass>, response: Response<MoviesDataClass>) {
                if (response.isSuccessful && response.body() != null) {
                    movie.set(response.body())
                } else {
                    isLoading.set(false)
                }
            }

            override fun onFailure(call: Call<MoviesDataClass>, t: Throwable) {
                isLoading.set(false)
            }
        }, id = imdbID)
    }
}
