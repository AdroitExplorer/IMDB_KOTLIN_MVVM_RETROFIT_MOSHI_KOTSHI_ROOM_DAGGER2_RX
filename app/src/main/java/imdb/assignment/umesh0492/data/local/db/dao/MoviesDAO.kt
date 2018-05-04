package imdb.assignment.umesh0492.data.local.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import imdb.assignment.umesh0492.data.model.MoviesDataClass

@Dao
interface MoviesDAO {

    @get:Query("SELECT * from movie_table")
    val allMovies: LiveData<List<MoviesDataClass>>

    @Query("SELECT * from movie_table Where imdbID = :id")
    fun getMovieById(id : String): MoviesDataClass

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(movie: MoviesDataClass)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movie: MoviesDataClass)

    @Query("DELETE FROM movie_table")
    fun deleteAll()
}
