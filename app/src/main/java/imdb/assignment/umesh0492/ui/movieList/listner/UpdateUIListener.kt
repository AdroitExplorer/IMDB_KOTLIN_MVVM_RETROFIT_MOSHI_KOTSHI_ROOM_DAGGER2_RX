package imdb.assignment.umesh0492.ui.movieList.listner

import imdb.assignment.umesh0492.data.model.MoviesDataClass

interface UpdateUIListener {
    fun startMovieDetailActivity(movies: MoviesDataClass)
}
