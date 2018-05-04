package imdb.assignment.umesh0492.data

import imdb.assignment.umesh0492.data.local.db.DbHelper
import imdb.assignment.umesh0492.data.local.pref.PreferencesHelper
import imdb.assignment.umesh0492.data.remote.ApiHelper

interface DataManager : DbHelper, PreferencesHelper, ApiHelper {

}
