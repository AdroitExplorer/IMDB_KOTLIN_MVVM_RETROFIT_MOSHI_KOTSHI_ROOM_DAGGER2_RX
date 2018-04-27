package imdb.assignment.umesh0492.ui.movieDetail.network

import imdb.assignment.umesh0492.App
import imdb.assignment.umesh0492.ui.movieList.data.model.MoviesDataClass
import retrofit2.Call
import retrofit2.Callback

class MoviesDetailRepository {

    fun getMovie(callback: Callback<MoviesDataClass>, id: String) {
        val service = App.instance.retrofit.create(MoviesDetailService::class.java)
        val news: Call<MoviesDataClass> = service.getMovie(id)
        news.enqueue(callback)
    }
}
