package imdb.assignment.umesh0492.ui.base

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import imdb.assignment.umesh0492.data.DataManager
import imdb.assignment.umesh0492.util.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel<T>
constructor(val dataManager: DataManager,
                    val schedulerProvider: SchedulerProvider) : ViewModel() {

    private val mIsLoading = ObservableBoolean(false)

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    fun getIsLoading(): ObservableBoolean {
        return mIsLoading
    }

    fun setIsLoading(isLoading: Boolean) {
        this.mIsLoading.set(isLoading)
    }
}
