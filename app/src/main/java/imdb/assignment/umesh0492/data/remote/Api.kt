package imdb.assignment.umesh0492.data.remote

import imdb.assignment.umesh0492.data.model.MoviesDataClass
import imdb.assignment.umesh0492.data.model.SearchDataClass
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("?apikey=e22bb521")
    fun getMovieList(@Query(value = "s") search: String, @Query(value = "page") pageNo: String): Single<SearchDataClass>

    @GET("?apikey=e22bb521")
    fun getMovieDetails(@Query(value = "i") id: String): Single<MoviesDataClass>
}
