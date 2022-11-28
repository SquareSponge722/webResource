package com.squaresponge.resource_web.interfaces.message_manage.dto

import com.squaresponge.resource_web.domain.message.Content
import com.squaresponge.resource_web.domain.message.Message
import com.squaresponge.resource_web.domain.message.MessageId
import com.squaresponge.resource_web.domain.message.Meta
import com.squaresponge.resource_web.domain.resource.ResourceId
import com.squaresponge.resource_web.domain.user.UserId
import java.sql.Date
import java.time.LocalDate

data class MessageInfo(
    val resourceId: String,
    val post: String
) {
    fun toMessage(userId: String, index: Int) = Message(
        messageId = MessageId(),
        content = Content(post),
        meta = Meta(
            from = UserId(userId),
            resourceId = ResourceId(resourceId),
            index = index,
            date = Date.valueOf(LocalDate.now())
        )
    )
}
