package imdb.assignment.umesh0492.ui.movieList.di

import dagger.Module
import dagger.Provides
import imdb.assignment.umesh0492.data.DataManager
import imdb.assignment.umesh0492.ui.movieList.MovieActivityVM
import imdb.assignment.umesh0492.util.rx.SchedulerProvider

@Module
class MovieListModule {

    @Provides
    fun provideMovieViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider): MovieActivityVM {
        return MovieActivityVM(dataManager, schedulerProvider)
    }
}
