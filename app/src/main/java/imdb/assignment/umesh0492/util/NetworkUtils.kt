package imdb.assignment.umesh0492.util

import android.content.Context
import android.net.ConnectivityManager


class NetworkUtils private constructor() {
    companion object {
       fun isNetworkConnected(context: Context): Boolean {
           val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
           val activeNetwork = cm.activeNetworkInfo
           return activeNetwork != null && activeNetwork.isConnectedOrConnecting
       }
   }
}
