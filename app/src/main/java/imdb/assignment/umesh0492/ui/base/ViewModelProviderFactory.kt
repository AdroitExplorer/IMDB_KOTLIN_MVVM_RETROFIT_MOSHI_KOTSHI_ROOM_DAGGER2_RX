package imdb.assignment.umesh0492.ui.base

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider


/**
 * A provider factory that persists ViewModels [ViewModel].
 * Used if the view model has a parameterized constructor.
 */
class ViewModelProviderFactory<V : Any>(private val viewModel: V) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(viewModel::class.java)) {
            return viewModel as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}
