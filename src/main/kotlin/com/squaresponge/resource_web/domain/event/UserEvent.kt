package com.squaresponge.resource_web.domain.event

import com.squaresponge.resource_web.domain.message.Message
import com.squaresponge.resource_web.domain.resource.Resource
import com.squaresponge.resource_web.domain.shared.Entity
import com.squaresponge.resource_web.domain.shared.ValueObject
import com.squaresponge.resource_web.domain.user.User

data class UserEvent(
    val id: UserEventId,
    val user: User,
    val type: Event,
    val resource: Resource?,
    val message: Message?
) : Entity<UserEvent> {

    override fun sameIdentityAs(other: UserEvent) = this.id == other.id

    enum class Event : ValueObject<Event> {
        UPLOAD, DOWNLOAD, MESSAGE, LOGIN
    }
}
