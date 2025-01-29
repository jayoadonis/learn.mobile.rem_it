package learn.mobile.rem_it.models

class User(
    private val userName: String?,
    private val email: String?,
    private val isLoggedIn: Boolean = false
) {

    public fun getEmail(): String? {
        return this.email;
    }

    public fun getUserName(): String? {
        return this.userName;
    }

    public fun isLoggedIn(): Boolean {
        return this.isLoggedIn;
    }
}