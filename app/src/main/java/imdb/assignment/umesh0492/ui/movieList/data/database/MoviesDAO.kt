package imdb.assignment.umesh0492.ui.movieList.data.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import imdb.assignment.umesh0492.ui.movieList.data.model.MoviesDataClass

@Dao
interface MoviesDAO {

    @get:Query("SELECT * from movie_table")
    val allMovies: LiveData<List<MoviesDataClass>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movie: MoviesDataClass)

    @Query("DELETE FROM movie_table")
    fun deleteAll()
}
