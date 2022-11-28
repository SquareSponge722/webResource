package com.squaresponge.resource_web.domain.user

import com.squaresponge.resource_web.domain.shared.ValueObject

data class Account(
    val name: String,
    val password: Password
) : ValueObject<Account> {

    constructor(name: String) : this(name, Password.UNKNOWN)

    init {
        if (name.isBlank()) throw IllegalArgumentException("account must have a non-blank name.")
    }
}