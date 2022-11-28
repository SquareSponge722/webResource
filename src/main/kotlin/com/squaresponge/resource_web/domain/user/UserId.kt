package com.squaresponge.resource_web.domain.user

import com.squaresponge.resource_web.domain.shared.ValueObject

data class UserId(val id: String? = null) : ValueObject<UserId> {

    val userId: String
        get() {
            if (id.isNullOrEmpty()) throw IllegalStateException(
                "no userId, " +
                        "please check the User's state that it has been registered"
            )
            return id
        }
}
