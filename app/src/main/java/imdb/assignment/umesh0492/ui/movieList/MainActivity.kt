package imdb.assignment.umesh0492.ui.movieList

import android.app.ActivityOptions
import android.content.Intent
import android.databinding.DataBindingUtil
import android.nfc.tech.MifareUltralight.PAGE_SIZE
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import imdb.assignment.umesh0492.base.BaseActivity
import imdb.assignment.umesh0492.network.NetworkCallListener
import imdb.assignment.umesh0492.ui.movieDetail.MovieDetailActivity
import imdb.assignment.umesh0492.ui.movieList.data.model.MoviesDataClass
import imdb.assignment.umesh0492.ui.movieList.data.model.SearchDataClass
import imdb.assignment.umesh0492.ui.movieList.listner.UpdateUIListener
import rentomojo.test.umesh0492.R
import rentomojo.test.umesh0492.databinding.ActivityMainBinding


class MainActivity : BaseActivity() {

    private lateinit var vm: MainActivityVM
    private lateinit var moviesAdapter: MoviesAdapter

    lateinit var binding: ActivityMainBinding
    lateinit var mLayoutManager: LinearLayoutManager

    var lastVisibleItemPosition: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        moviesAdapter = MoviesAdapter()
        vm = MainActivityVM(this)
        moviesAdapter.attachVm(vm)
        binding.recyclerView.adapter = moviesAdapter
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount = mLayoutManager.childCount
                val totalItemCount = mLayoutManager.itemCount
                if (mLayoutManager.findFirstVisibleItemPosition() > 0) {
                    lastVisibleItemPosition = mLayoutManager.findLastCompletelyVisibleItemPosition()
                }

                if (!vm.isLoading.get() && !vm.isLastPage) {
                    if (visibleItemCount + lastVisibleItemPosition >= totalItemCount
                            && lastVisibleItemPosition >= 0
                            && totalItemCount >= PAGE_SIZE) {
                        getMoviesFromServer()
                        binding.moreProgressBar.visibility = View.VISIBLE
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
                binding.recyclerView.smoothScrollToPosition(lastVisibleItemPosition)
            }, 200)
        }

    }

    override fun setUpView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mLayoutManager = LinearLayoutManager(applicationContext)
        binding.recyclerView.layoutManager = mLayoutManager
    }

    private fun setupUIListener() {
        vm.setUIListener(object : UpdateUIListener {
            override fun startMovieDetailActivity(movies: MoviesDataClass) {
                var intent = Intent(this@MainActivity, MovieDetailActivity::class.java)

                intent.putExtra("Title", movies.Title)
                intent.putExtra("Poster", movies.Poster)
                intent.putExtra("imdbID", movies.imdbID)
                intent.putExtra("Year", movies.Year)

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this@MainActivity).toBundle())
                } else {
                    startActivity(intent)
                }
            }

            override fun updateUI() {
                updateView()
            }
        })
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putParcelable("vm", vm)
        outState?.putInt("pos", lastVisibleItemPosition)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState != null) {
            vm = savedInstanceState.getParcelable("vm")
            lastVisibleItemPosition = savedInstanceState.getInt("pos")
            setupUIListener()
        }
    }

    private fun getMoviesFromServer() {
        vm.getMovieList(object : NetworkCallListener<SearchDataClass> {
            override fun onSuccess(data: SearchDataClass) {
                binding.moreProgressBar.visibility = View.GONE
            }

            override fun onFailure() {
                Toast.makeText(this@MainActivity, getString(R.string.network_issue), Toast.LENGTH_LONG).show()
                binding.moreProgressBar.visibility = View.GONE
            }
        })
    }

    private fun updateView() {
        updateAdapter()
    }

    private fun updateAdapter() {
        moviesAdapter.insertList(vm.movieList.value!!.toMutableList())
    }
}
