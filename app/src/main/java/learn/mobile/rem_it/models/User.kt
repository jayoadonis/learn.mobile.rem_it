package learn.mobile.rem_it.models

data class User(
    public val _ID: Int?,
    public val userID: String?,
    public val userName: String?,
    public val email: String?,
    public val isLoggedIn: Boolean = false,
    public val password: String = "[protected]"
) {

//    public fun getEmail(): String? {
//        return this.email;
//    }
//
//    public fun getUserName(): String? {
//        return this.userName;
//    }
//
//    public fun isLoggedIn(): Boolean {
//        return this.isLoggedIn;
//    }
}