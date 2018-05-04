package imdb.assignment.umesh0492.data.local.pref

import android.content.Context
import android.content.SharedPreferences
import imdb.assignment.umesh0492.di.qualifiers.PreferenceInfo
import javax.inject.Inject

class AppPreferences
@Inject constructor(context: Context, @PreferenceInfo prefFileName: String) : PreferencesHelper {

    private val mPrefs: SharedPreferences = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE)

    private val PREF_KEY_CURRENT_USER_NAME = "PREF_KEY_CURRENT_USER_NAME"

    override var currentUserName: String
        get() = mPrefs.getString(PREF_KEY_CURRENT_USER_NAME, null)
        set(userName) = mPrefs.edit().putString(PREF_KEY_CURRENT_USER_NAME, userName).apply()


}
