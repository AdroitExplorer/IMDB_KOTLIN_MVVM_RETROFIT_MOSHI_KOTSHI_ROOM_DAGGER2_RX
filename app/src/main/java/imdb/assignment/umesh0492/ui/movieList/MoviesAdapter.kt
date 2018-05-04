package imdb.assignment.umesh0492.ui.movieList

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import imdb.assignment.umesh0492.data.model.MoviesDataClass
import imdb.assignment.umesh0492.databinding.ItemMovieBinding

class MoviesAdapter : RecyclerView.Adapter<MoviesAdapter.MovieItemViewHolder>() {

    var movieList: MutableList<MoviesDataClass> = ArrayList()

    private lateinit var vm: MovieActivityVM
    override fun onBindViewHolder(holder: MovieItemViewHolder, position: Int) {
        Glide.with(holder.binding.poster).load(movieList[position].Poster).into(holder.binding.poster)
        holder.binding.item = movieList[position]
        holder.binding.vm = vm
        holder.binding.executePendingBindings()
    }

    fun insertList(movieList: MutableList<MoviesDataClass>) {
        val count = this.movieList.size
        this.movieList = movieList
        this.notifyItemInserted(count)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieItemViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieItemViewHolder(binding)
    }

    inner class MovieItemViewHolder(val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root)

    fun attachVm(vm: MovieActivityVM) {
        this.vm = vm
    }
}
