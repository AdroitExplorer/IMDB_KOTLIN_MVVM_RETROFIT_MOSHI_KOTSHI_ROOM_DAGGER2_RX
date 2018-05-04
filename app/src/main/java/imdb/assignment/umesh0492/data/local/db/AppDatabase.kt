package imdb.assignment.umesh0492.data.local.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import imdb.assignment.umesh0492.data.local.db.dao.MoviesDAO
import imdb.assignment.umesh0492.data.model.MoviesDataClass

@Database(entities = [(MoviesDataClass::class)], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun moviesDAO(): MoviesDAO
}
