package imdb.assignment.umesh0492.data.remote

import imdb.assignment.umesh0492.data.model.MoviesDataClass
import imdb.assignment.umesh0492.data.model.SearchDataClass
import io.reactivex.Single

interface ApiHelper {
    fun getMovieDetailsApiCall(id: String): Single<MoviesDataClass>
    fun getMovieListApiCall(pageNo: Int): Single<SearchDataClass>
}
