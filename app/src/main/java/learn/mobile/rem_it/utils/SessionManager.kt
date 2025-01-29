package learn.mobile.rem_it.utils

import android.content.Context
import android.content.SharedPreferences
import learn.mobile.rem_it.models.User

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
    }

    public fun saveUserSession(
        user: User
    ): Unit {
        this.sessionController.putBoolean(SessionManager.IS_LOGGED_IN, user.isLoggedIn() );
        this.sessionController.putString(SessionManager.USER_NAME, user.getUserName() );
        this.sessionController.apply();
    }

    public fun isLoggedIn(): Boolean {
        return this.session.getBoolean(SessionManager.IS_LOGGED_IN, false);
    }

    public fun getUserName(): String? {
        return this.session.getString(SessionManager.USER_NAME, null);
    }

    public fun clearSession(): Unit {
        this.sessionController.putBoolean(IS_LOGGED_IN, false);
        this.sessionController.putString(USER_NAME, null);
        this.sessionController.apply();
    }

}