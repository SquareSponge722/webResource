package com.squaresponge.resource_web.infrastructure

import com.squaresponge.resource_web.domain.message.Message
import com.squaresponge.resource_web.domain.message.MessageId
import com.squaresponge.resource_web.domain.message.repository.MessageRepository
import com.squaresponge.resource_web.domain.resource.ResourceId
import com.squaresponge.resource_web.domain.user.UserId
import com.squaresponge.resource_web.infrastructure.persistence.mybatis.mapper.MessageMapper
import com.squaresponge.resource_web.infrastructure.persistence.mybatis.po.MessagePo
import org.springframework.stereotype.Repository

@Repository
class MessageRepositoryImpl(private val messageMapper: MessageMapper) : MessageRepository {
    override fun findMessages(messageId: MessageId?, userId: UserId?, resourceId: ResourceId?): List<Message> {
        val messages = messageMapper.findMessage(
            id = messageId?.id?.toLong(),
            userId = userId?.id?.toLong(),
            resourceId = resourceId?.resourceId?.toLong()
        )
        return messages.map { it.toMessage() }
    }

    override fun saveMessage(message: Message) {
        messageMapper.saveMessage(MessagePo.ofMessage(message))
    }

    override fun deleteMessage(messageId: MessageId?, resourceId: ResourceId?) {
        messageMapper.deleteMessage(
            id = messageId?.id?.toLong(),
            resourceId = resourceId?.resourceId?.toLong()
        )
    }

    override fun generateNextIndex(resourceId: ResourceId): Int {
        return messageMapper.generateNextIndex(resourceId.resourceId!!.toLong()) ?: 1
    }
}