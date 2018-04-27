package imdb.assignment.umesh0492.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import imdb.assignment.umesh0492.ui.movieList.data.database.MoviesDAO
import imdb.assignment.umesh0492.ui.movieList.data.model.MoviesDataClass

@Database(entities = [(MoviesDataClass::class)], version = 1)
abstract class MoviesDatabase : RoomDatabase() {

    abstract fun getMoviesDAO(): MoviesDAO

    companion object {
        private var INSTANCE: MoviesDatabase? = null
        fun getDatabase(context: Context): MoviesDatabase? {
            if (INSTANCE == null) {
                synchronized(MoviesDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.applicationContext,
                                MoviesDatabase::class.java, "movies_database")
                                .build()
                    }
                }
            }
            return INSTANCE
        }
    }
}
