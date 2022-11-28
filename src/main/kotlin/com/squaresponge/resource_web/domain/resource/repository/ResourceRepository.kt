package com.squaresponge.resource_web.domain.resource.repository

import com.squaresponge.resource_web.domain.resource.Resource
import com.squaresponge.resource_web.domain.resource.ResourceId
import com.squaresponge.resource_web.domain.resource.Title
import com.squaresponge.resource_web.domain.user.UserId

interface ResourceRepository {
    fun findResources(
        resourceId: ResourceId? = null,
        title: Title? = null,
        userId: UserId? = null
    ): List<Resource>

    fun saveResource(resource: Resource): ResourceId

    fun deleteResource(resourceId: ResourceId)
}