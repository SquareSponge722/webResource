package com.squaresponge.resource_web.domain.user.exception

class PasswordIncorrectException(private val username: String) : Exception() {
    override val message: String
        get() = "user $username password incorrect."
}