package com.squaresponge.resource_web.domain.message.repository

import com.squaresponge.resource_web.domain.message.Message
import com.squaresponge.resource_web.domain.message.MessageId
import com.squaresponge.resource_web.domain.resource.ResourceId
import com.squaresponge.resource_web.domain.user.UserId

interface MessageRepository {
    fun findMessages(
        messageId: MessageId? = null,
        userId: UserId? = null,
        resourceId: ResourceId? = null
    ): List<Message>

    fun saveMessage(message: Message)

    fun deleteMessage(
        messageId: MessageId? = null,
        resourceId: ResourceId? = null
    )

    fun generateNextIndex(resourceId: ResourceId): Int
}