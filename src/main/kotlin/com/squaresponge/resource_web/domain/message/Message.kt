package com.squaresponge.resource_web.domain.message

import com.squaresponge.resource_web.domain.shared.Entity

data class Message(
    val messageId: MessageId,
    var content: Content,
    var meta: Meta
) : Entity<Message> {

    override fun sameIdentityAs(other: Message) = this.messageId == other.messageId
}
