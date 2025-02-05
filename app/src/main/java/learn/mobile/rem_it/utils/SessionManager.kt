package learn.mobile.rem_it.utils

import android.content.Context
import android.content.SharedPreferences
import learn.mobile.rem_it.models.User
import java.security.InvalidParameterException

class SessionManager(context: Context) {

    private val session: SharedPreferences
        = context.getSharedPreferences(
            "UserSession",
            Context.MODE_PRIVATE
        );
    private val sessionController: SharedPreferences.Editor
        = this.session.edit();

    public companion object {
        private const val IS_LOGGED_IN = "isLoggedIn";
        private const val USER_NAME = "userName";
        private const val USER_ID = "user_id";
        private const val _ID = "_id";
    }

    public fun saveUserSession(
        user: User
    ): Unit {
        if( user._ID == null || user._ID == 0)
            throw InvalidParameterException("Invalid ID to be 'cookified' or session.")
        this.sessionController.putInt(SessionManager._ID, user._ID );
        this.sessionController.putBoolean(SessionManager.IS_LOGGED_IN, user.isLoggedIn );
        this.sessionController.putString(SessionManager.USER_NAME, user.userName );
        this.sessionController.putString(SessionManager.USER_ID, user.userID );
        this.sessionController.apply();
    }

    public fun isLoggedIn(): Boolean {
        return this.session.getBoolean(SessionManager.IS_LOGGED_IN, false);
    }

    public fun getUserName(): String? {
        return this.session.getString(SessionManager.USER_NAME, null);
    }

    public fun getUserId(): String? {
        return this.session.getString( USER_ID, null);
    }

    public fun getId(): Int {
        return this.session.getInt( _ID, 0);
    }

    public fun clearSession(): Unit {
        this.sessionController.putBoolean(IS_LOGGED_IN, false);
        this.sessionController.putString(USER_NAME, null);
        this.sessionController.apply();
    }

}