package com.squaresponge.resource_web.domain.resource

import com.squaresponge.resource_web.domain.shared.Entity
import com.squaresponge.resource_web.domain.user.UserId

data class Resource(
    val resourceId: ResourceId,
    var profile: Profile,
    var document: Document,
    var userId: UserId
) : Entity<Resource> {

    override fun sameIdentityAs(other: Resource) = this.resourceId == other.resourceId
}
