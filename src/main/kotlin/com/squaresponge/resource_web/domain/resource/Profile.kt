package com.squaresponge.resource_web.domain.resource

import com.squaresponge.resource_web.domain.shared.ValueObject

data class Profile(
    val title: Title,
    val introduction: String
) : ValueObject<Profile>
