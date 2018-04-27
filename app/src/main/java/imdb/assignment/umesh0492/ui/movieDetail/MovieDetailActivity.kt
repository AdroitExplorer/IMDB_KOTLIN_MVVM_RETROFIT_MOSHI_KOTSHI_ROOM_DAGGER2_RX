package imdb.assignment.umesh0492.ui.movieDetail

import android.databinding.DataBindingUtil
import android.os.Build
import android.os.Bundle
import android.transition.Explode
import android.view.Window
import com.bumptech.glide.Glide
import imdb.assignment.umesh0492.base.BaseActivity
import imdb.assignment.umesh0492.ui.movieList.data.model.MoviesDataClass
import rentomojo.test.umesh0492.R
import rentomojo.test.umesh0492.databinding.ActivityMovieDetailBinding

class MovieDetailActivity : BaseActivity() {

    lateinit var imdbID: String
    lateinit var binding: ActivityMovieDetailBinding
    lateinit var vm: MovieDetailActivityVM
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val titleString = intent.getStringExtra("Title")
        val yearString = intent.getStringExtra("Year")
        imdbID = intent.getStringExtra("imdbID")
        val posterUrl = intent.getStringExtra("Poster")

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

        vm = MovieDetailActivityVM(movie)
        vm.getMovie(imdbID)
        binding.vm = vm
    }

    override fun setUpView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            with(window) {
                requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
                // set an exit transition
                exitTransition = Explode()
            }
        }
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
