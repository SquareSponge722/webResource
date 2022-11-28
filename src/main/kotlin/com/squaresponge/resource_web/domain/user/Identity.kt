package com.squaresponge.resource_web.domain.user

import com.squaresponge.resource_web.domain.shared.ValueObject

enum class Identity : ValueObject<Identity> {

    USER, ADMIN;
}