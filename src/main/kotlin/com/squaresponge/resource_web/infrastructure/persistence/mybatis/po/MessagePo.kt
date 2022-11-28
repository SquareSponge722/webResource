package com.squaresponge.resource_web.infrastructure.persistence.mybatis.po

import com.squaresponge.resource_web.domain.message.Content
import com.squaresponge.resource_web.domain.message.Message
import com.squaresponge.resource_web.domain.message.MessageId
import com.squaresponge.resource_web.domain.message.Meta
import com.squaresponge.resource_web.domain.resource.ResourceId
import com.squaresponge.resource_web.domain.user.UserId
import java.sql.Date

data class MessagePo(
    val id: Long? = null,
    val userId: Long,
    val resourceId: Long,
    val text: String,
    val index: Int,
    val post: Date
) {

    companion object {
        fun ofMessage(message: Message) = MessagePo(
            id = message.messageId.id?.toLong(),
            userId = message.meta.from.userId.toLong(),
            resourceId = message.meta.resourceId.resourceId!!.toLong(),
            text = message.content.text,
            index = message.meta.index,
            post = message.meta.date
        )
    }

    fun toMessage() = Message(
        messageId = MessageId(id!!.toString()),
        content = Content(text = text),
        meta = Meta(
            from = UserId(userId.toString()),
            resourceId = ResourceId(resourceId.toString()),
            index = index,
            date = post
        )
    )
}
