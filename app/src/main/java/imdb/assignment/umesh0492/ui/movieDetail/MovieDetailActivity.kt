package imdb.assignment.umesh0492.ui.movieDetail

import android.os.Build
import android.os.Bundle
import android.transition.Explode
import android.view.Window
import com.bumptech.glide.Glide
import imdb.assignment.umesh0492.BR
import imdb.assignment.umesh0492.R
import imdb.assignment.umesh0492.data.model.MoviesDataClass
import imdb.assignment.umesh0492.databinding.ActivityMovieDetailBinding
import imdb.assignment.umesh0492.ui.base.BaseActivity
import javax.inject.Inject

class MovieDetailActivity : BaseActivity<ActivityMovieDetailBinding, MovieDetailActivityVM>() {

    lateinit var imdbID: String
    lateinit var binding: ActivityMovieDetailBinding

    @Inject
    override
    lateinit var viewModel: MovieDetailActivityVM

    override val bindingVariable: Int
        get() = BR.vm

    override val layoutId: Int
        get() = R.layout.activity_movie_detail


    override fun onCreate(savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            with(window) {
                requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
                // set an exit transition
                exitTransition = Explode()
            }
        }
        super.onCreate(savedInstanceState)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val titleString = intent.getStringExtra("Title")
        val yearString = intent.getStringExtra("Year")
        imdbID = intent.getStringExtra("imdbID")
        val posterUrl = intent.getStringExtra("Poster")

        binding = viewDataBinding!!

        binding.movieTitle.text = titleString
        binding.year.text = yearString

        Glide.with(binding.poster).load(posterUrl).into(binding.poster)

        var movie = MoviesDataClass(Title = titleString,
                Year = yearString,
                Poster = posterUrl,
                imdbID = imdbID,
                Actors = "",
                Director = "",
                Plot = "",
                Genre = "",
                imdbRating = "",
                imdbVotes = "",
                Language = "",
                Writer = "")

        viewModel.movie.set(movie)

        viewModel.getMovie(imdbID)
        binding.vm = viewModel
    }

    /*override fun setUpView() {

    }*/
}
