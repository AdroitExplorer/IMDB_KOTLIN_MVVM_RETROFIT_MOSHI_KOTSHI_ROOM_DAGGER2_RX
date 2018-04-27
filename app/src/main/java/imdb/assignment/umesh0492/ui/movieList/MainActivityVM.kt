package imdb.assignment.umesh0492.ui.movieList

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.databinding.ObservableBoolean
import android.os.Parcel
import android.os.Parcelable
import imdb.assignment.umesh0492.network.NetworkCallListener
import imdb.assignment.umesh0492.ui.movieList.data.model.MoviesDataClass
import imdb.assignment.umesh0492.ui.movieList.data.model.SearchDataClass
import imdb.assignment.umesh0492.ui.movieList.data.network.MoviesRepository
import imdb.assignment.umesh0492.ui.movieList.listner.UpdateUIListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityVM() : Parcelable {

    private val repo: MoviesRepository = MoviesRepository()
    var movieList: LiveData<List<MoviesDataClass>> = repo.getMovieList()

    lateinit var uiListener: UpdateUIListener

    var isLoading: ObservableBoolean = ObservableBoolean(false)
    var isLastPage: Boolean = false
    private var pageNo: Int = 1

    constructor(parcel: Parcel) : this()

    constructor(activity: MainActivity) : this() {
        movieList.observe(activity, Observer<List<MoviesDataClass>> {
            uiListener.updateUI()
        })
    }

    fun setUIListener(listener: UpdateUIListener) {
        this.uiListener = listener
    }

    fun getMovieList(listener: NetworkCallListener<SearchDataClass>) {
        isLoading.set(true)
        repo.getMovieList(object : Callback<SearchDataClass> {
            override fun onResponse(call: Call<SearchDataClass>, response: Response<SearchDataClass>) {
                if (response.body() != null && response.body()!!.Search.isNotEmpty()) {
                    val data = response.body()!!
                    isLoading.set(false)
                    if (movieList.value!!.isNotEmpty()) {
                        pageNo = movieList.value!!.size / data.Search.size
                    }
                    pageNo++
                    isLastPage = (data.totalResults.toInt() / data.Search.size) < 1
                    uiListener.updateUI()
                    listener.onSuccess(response.body()!!)

                } else {
                    isLoading.set(false)
                    listener.onFailure()
                }
            }

            override fun onFailure(call: Call<SearchDataClass>, t: Throwable) {
                isLoading.set(false)
                listener.onFailure()
            }
        }, pageNo)
    }

    fun onItemClicked(movies: MoviesDataClass) {
        uiListener.startMovieDetailActivity(movies)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MainActivityVM> {
        override fun createFromParcel(parcel: Parcel): MainActivityVM {
            return MainActivityVM(parcel)
        }

        override fun newArray(size: Int): Array<MainActivityVM?> {
            return arrayOfNulls(size)
        }
    }
}
