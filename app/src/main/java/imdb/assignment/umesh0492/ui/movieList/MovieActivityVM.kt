package imdb.assignment.umesh0492.ui.movieList

import android.arch.lifecycle.LiveData
import imdb.assignment.umesh0492.data.DataManager
import imdb.assignment.umesh0492.data.model.MoviesDataClass
import imdb.assignment.umesh0492.data.model.SearchDataClass
import imdb.assignment.umesh0492.network.NetworkCallListener
import imdb.assignment.umesh0492.ui.base.BaseViewModel
import imdb.assignment.umesh0492.ui.movieList.listner.UpdateUIListener
import imdb.assignment.umesh0492.util.rx.SchedulerProvider
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import android.arch.lifecycle.MediatorLiveData
import android.databinding.adapters.NumberPickerBindingAdapter.setValue
import android.support.annotation.Nullable


class MovieActivityVM(dataManager: DataManager,
                      schedulerProvider: SchedulerProvider)
    : BaseViewModel<Any?>(dataManager = dataManager, schedulerProvider = schedulerProvider) {

    var movieList: LiveData<List<MoviesDataClass>> = dataManager.getMovieListFromDB()

    lateinit var uiListener: UpdateUIListener

    var isLastPage: Boolean = false
    private var pageNo: Int = 1

    fun setUIListener(listener: UpdateUIListener) {
        this.uiListener = listener
    }

    fun getMovieList(listener: NetworkCallListener<SearchDataClass>) {
        setIsLoading(true)

        compositeDisposable.add(dataManager.getMovieListApiCall(pageNo)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe({ movieResponse ->
                    setIsLoading(false)
                    if (movieList.value!=null && movieList.value!!.isNotEmpty()) {
                        pageNo = movieList.value!!.size / movieResponse.Search.size
                    }
                    pageNo++
                    isLastPage = (movieResponse.totalResults.toInt() / movieResponse.Search.size) < 1
                    listener.onSuccess(movieResponse)
                },
                        {
                            setIsLoading(false)
                            listener.onFailure()
                        }))

    }

    fun onItemClicked(movies: MoviesDataClass) {
        uiListener.startMovieDetailActivity(movies)
    }
}
