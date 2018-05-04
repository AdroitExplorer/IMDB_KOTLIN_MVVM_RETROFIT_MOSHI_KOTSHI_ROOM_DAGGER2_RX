package imdb.assignment.umesh0492.ui.movieDetail

import android.databinding.ObservableField
import imdb.assignment.umesh0492.data.DataManager
import imdb.assignment.umesh0492.data.model.MoviesDataClass
import imdb.assignment.umesh0492.ui.base.BaseViewModel
import imdb.assignment.umesh0492.util.rx.SchedulerProvider
import io.reactivex.schedulers.Schedulers

class MovieDetailActivityVM(movie: MoviesDataClass, dataManager: DataManager,
                            schedulerProvider: SchedulerProvider)
    : BaseViewModel<Any?>(dataManager = dataManager, schedulerProvider = schedulerProvider) {

    var movie: ObservableField<MoviesDataClass> = ObservableField(movie)

    fun getMovie(imdbID: String) {
        setIsLoading(true)

        compositeDisposable.add(dataManager.getMovieDetailsApiCall(imdbID)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.io())
                .subscribe({ movieResponse ->
                    setIsLoading(false)
                    movie.set(movieResponse)
                },
                        {
                            setIsLoading(false)
                        }))
    }
}
