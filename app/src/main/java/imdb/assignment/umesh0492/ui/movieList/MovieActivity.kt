package imdb.assignment.umesh0492.ui.movieList

import android.app.ActivityOptions
import android.content.Intent
import android.databinding.Observable
import android.nfc.tech.MifareUltralight.PAGE_SIZE
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.Toast
import dagger.android.AndroidInjection
import imdb.assignment.umesh0492.BR
import imdb.assignment.umesh0492.R
import imdb.assignment.umesh0492.data.model.MoviesDataClass
import imdb.assignment.umesh0492.data.model.SearchDataClass
import imdb.assignment.umesh0492.databinding.ActivityMainBinding
import imdb.assignment.umesh0492.network.NetworkCallListener
import imdb.assignment.umesh0492.ui.base.BaseActivity
import imdb.assignment.umesh0492.ui.movieDetail.MovieDetailActivity
import imdb.assignment.umesh0492.ui.movieList.listner.UpdateUIListener
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MovieActivity : BaseActivity<ActivityMainBinding, MovieActivityVM>() {

    @Inject
    override
    lateinit var viewModel: MovieActivityVM

    override val bindingVariable: Int
        get() = BR.vm

    override val layoutId: Int
        get() = R.layout.activity_main

    private lateinit var moviesAdapter: MoviesAdapter

    lateinit var mLayoutManager: LinearLayoutManager

    var lastVisibleItemPosition: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)

        mLayoutManager = LinearLayoutManager(applicationContext)
        viewDataBinding!!.recyclerView.layoutManager = mLayoutManager
        moviesAdapter = MoviesAdapter()
        moviesAdapter.attachVm(viewModel)
        viewDataBinding!!.recyclerView.adapter = moviesAdapter
        viewDataBinding!!.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount = mLayoutManager.childCount
                val totalItemCount = mLayoutManager.itemCount
                if (mLayoutManager.findFirstVisibleItemPosition() > 0) {
                    lastVisibleItemPosition = mLayoutManager.findLastCompletelyVisibleItemPosition()
                }

                if (!viewModel.getIsLoading().get() && !viewModel.isLastPage) {
                    if (visibleItemCount + lastVisibleItemPosition >= totalItemCount
                            && lastVisibleItemPosition >= 0
                            && totalItemCount >= PAGE_SIZE) {
                        getMoviesFromServer()
                        viewDataBinding!!.moreProgressBar.visibility = View.VISIBLE
                    }
                }
            }
        })
        setupUIListener()
        getMoviesFromServer()
    }

    override fun onResume() {
        super.onResume()
        if (lastVisibleItemPosition > 0) {
            Handler().postDelayed({
                viewDataBinding!!.recyclerView.smoothScrollToPosition(lastVisibleItemPosition)
            }, 200)
        }
    }

    private fun setupUIListener() {
        viewModel.setUIListener(object : UpdateUIListener {
            override fun startMovieDetailActivity(movies: MoviesDataClass) {
                var intent = Intent(this@MovieActivity, MovieDetailActivity::class.java)

                intent.putExtra("Title", movies.Title)
                intent.putExtra("Poster", movies.Poster)
                intent.putExtra("imdbID", movies.imdbID)
                intent.putExtra("Year", movies.Year)

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this@MovieActivity).toBundle())
                } else {
                    startActivity(intent)
                }
            }
        })

        viewModel.movieList.observeForever({
            if (it != null)
                moviesAdapter.insertList(it.toMutableList())
        })

        viewModel.getIsLoading().addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                Log.e("getIsLoading",propertyId.toString())
            }
        })
    }

    //todo handle config change via Rx-loader
    /* override fun onSaveInstanceState(outState: Bundle?) {
         outState?.putParcelable("vm", viewModel)
         outState?.putInt("pos", lastVisibleItemPosition)
         super.onSaveInstanceState(outState)
     }

     override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
         super.onRestoreInstanceState(savedInstanceState)
         if (savedInstanceState != null) {
             viewModel = savedInstanceState.getParcelable("vm")
             lastVisibleItemPosition = savedInstanceState.getInt("pos")
             setupUIListener()
         }
     }*/

    private fun getMoviesFromServer() {
        more_progress_bar.visibility = View.VISIBLE
        viewModel.getMovieList(object : NetworkCallListener<SearchDataClass> {
            override fun onSuccess(data: SearchDataClass) {
                more_progress_bar.visibility = View.GONE
            }

            override fun onFailure() {
                more_progress_bar.visibility = View.GONE
                Toast.makeText(this@MovieActivity, getString(R.string.network_issue), Toast.LENGTH_LONG).show()
            }
        })
    }
}
