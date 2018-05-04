package imdb.assignment.umesh0492.data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.databinding.BaseObservable
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
@Entity(tableName = "movie_table")
data class MoviesDataClass(var Title: String,
                           var Year: String,
                           @PrimaryKey val imdbID: String,
                           var Poster: String,
                           var Genre: String?,
                           var Director: String?,
                           var Writer: String?,
                           var Actors: String?,
                           var Plot: String?,
                           var Language: String?,
                           var imdbRating: String?,
                           var imdbVotes: String?) : BaseObservable()
