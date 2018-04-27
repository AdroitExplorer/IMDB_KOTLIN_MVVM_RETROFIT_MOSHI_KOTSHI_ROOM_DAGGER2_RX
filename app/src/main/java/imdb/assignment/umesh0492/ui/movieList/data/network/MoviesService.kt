package imdb.assignment.umesh0492.ui.movieList.data.network

import imdb.assignment.umesh0492.ui.movieList.data.model.SearchDataClass
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesService {
    @GET("?apikey=e22bb521")
    fun getMovieList(@Query(value = "s") search: String, @Query(value = "page") pageNo: String): Call<SearchDataClass>
}
