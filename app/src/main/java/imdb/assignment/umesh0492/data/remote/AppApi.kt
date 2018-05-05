package imdb.assignment.umesh0492.data.remote

import imdb.assignment.umesh0492.data.model.MoviesDataClass
import imdb.assignment.umesh0492.data.model.SearchDataClass
import io.reactivex.Single
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppApi
@Inject constructor(var retrofit: Retrofit) : ApiHelper {

    override fun getMovieDetailsApiCall(id: String): Single<MoviesDataClass> {
        val service = retrofit.create(Api::class.java)
        return service.getMovieDetails(id)
    }

    override fun getMovieListApiCall(pageNo: Int): Single<SearchDataClass> {
        val service = retrofit.create(Api::class.java)
        return service.getMovieList("an", pageNo = pageNo.toString())
    }
}
