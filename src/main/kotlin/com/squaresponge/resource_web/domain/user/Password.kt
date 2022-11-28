package com.squaresponge.resource_web.domain.user

import com.squaresponge.resource_web.domain.shared.ValueObject

data class Password(
    val password: String?,
    val type: Type
) : ValueObject<Password> {

    init {
        if (type != Type.UNKNOWN && password.isNullOrBlank()) {
            throw IllegalArgumentException("password must not be null.")
        }
    }

    companion object {
        val UNKNOWN = Password(null, Type.UNKNOWN)

        fun raw(password: String) = Password(password, Type.RAW)

        fun encoded(password: String) = Password(password, Type.ENCODED)
    }

    val passwordRaw: String
        get() = when (type) {
            Type.RAW -> password!!
            else -> throw IllegalStateException("incorrect password type $type")
        }

    val passwordEncoded: String
        get() = when (type) {
            Type.ENCODED -> password!!
            else -> throw IllegalStateException("incorrect password type $type")
        }

    enum class Type {
        UNKNOWN, RAW, ENCODED
    }
}