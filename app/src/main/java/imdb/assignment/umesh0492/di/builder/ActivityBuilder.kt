package imdb.assignment.umesh0492.di.builder

import dagger.Module
import dagger.android.ContributesAndroidInjector
import imdb.assignment.umesh0492.ui.movieDetail.MovieDetailActivity
import imdb.assignment.umesh0492.ui.movieList.MovieActivity
import imdb.assignment.umesh0492.ui.movieList.di.MovieDetailModule
import imdb.assignment.umesh0492.ui.movieList.di.MovieListModule


@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [MovieListModule::class])
    abstract fun bindMovieActivity(): MovieActivity

    @ContributesAndroidInjector(modules = [MovieDetailModule::class])
    abstract fun bindMovieDetailActivity(): MovieDetailActivity
}
