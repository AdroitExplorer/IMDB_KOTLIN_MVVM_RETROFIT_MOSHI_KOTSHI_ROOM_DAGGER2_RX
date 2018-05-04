package imdb.assignment.umesh0492.ui.movieList.di

import dagger.Module
import dagger.Provides
import imdb.assignment.umesh0492.data.DataManager
import imdb.assignment.umesh0492.data.model.MoviesDataClass
import imdb.assignment.umesh0492.ui.movieDetail.MovieDetailActivityVM
import imdb.assignment.umesh0492.util.rx.SchedulerProvider


@Module
class MovieDetailModule{

   /* @Provides
    fun movieViewModelProvider(movieViewModel: MovieActivityVM): ViewModelProvider.Factory {
        return ViewModelProviderFactory(movieViewModel)
    }*/

    @Provides
    fun provideMovieDetailViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider): MovieDetailActivityVM {
        return MovieDetailActivityVM(MoviesDataClass("",
                "","","","",
                "","","","","","",""),
                dataManager, schedulerProvider)
    }
}
