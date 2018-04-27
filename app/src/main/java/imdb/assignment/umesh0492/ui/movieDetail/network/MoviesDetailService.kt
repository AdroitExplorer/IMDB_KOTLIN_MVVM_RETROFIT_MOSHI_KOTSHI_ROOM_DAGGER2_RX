package imdb.assignment.umesh0492.ui.movieDetail.network

import imdb.assignment.umesh0492.ui.movieList.data.model.MoviesDataClass
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesDetailService {
    @GET("?apikey=e22bb521")
    fun getMovie(@Query(value = "i") id: String): Call<MoviesDataClass>
}
