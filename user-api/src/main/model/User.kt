package model

import utils.Constants

data class User(val name: String, val email: String, val _id: String?, val role: String = Constants.USER_ROLE) {
    fun isAdmin(): Boolean {
        return this.role == Constants.ADMIN_ROLE
    }
}