package imdb.assignment.umesh0492.ui.movieList.listner

import imdb.assignment.umesh0492.ui.movieList.data.model.MoviesDataClass

interface UpdateUIListener {
    fun updateUI()
    fun startMovieDetailActivity(movies: MoviesDataClass)
}
