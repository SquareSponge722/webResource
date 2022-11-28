package com.squaresponge.resource_web.interfaces.resource_manage.dto

import com.squaresponge.resource_web.domain.resource.*
import com.squaresponge.resource_web.domain.user.User

data class ResourceInfo(
    val id: String? = null,
    val title: String,
    val description: String
) {
    fun toResource(user: User) = Resource(
        resourceId = ResourceId(id),
        profile = Profile(
            title = Title(title),
            introduction = description
        ),
        document = Document(),
        userId = user.userId
    )
}
