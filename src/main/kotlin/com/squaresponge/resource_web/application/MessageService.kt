package com.squaresponge.resource_web.application

import com.squaresponge.resource_web.domain.message.repository.MessageRepository
import com.squaresponge.resource_web.domain.resource.ResourceId
import com.squaresponge.resource_web.domain.user.Account
import com.squaresponge.resource_web.domain.user.repository.UserRepository
import com.squaresponge.resource_web.interfaces.message_manage.dto.MessageDetailInfo
import com.squaresponge.resource_web.interfaces.message_manage.dto.MessageInfo
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MessageService(
    private val userRepository: UserRepository,
    private val messageRepository: MessageRepository
) {
    @Transactional
    @Synchronized
    fun postMessage(username: String, messageInfo: MessageInfo) {
        val user = userRepository.findUser(Account(username))
        val index = messageRepository.generateNextIndex(ResourceId(messageInfo.resourceId))
        val message = messageInfo.toMessage(user.userId.userId, index)
        messageRepository.saveMessage(message)
    }

    fun findMessages(resourceId: String): List<MessageDetailInfo> {
        val messages = messageRepository.findMessages(resourceId = ResourceId(resourceId))
        return messages.map { MessageDetailInfo.ofMessage(it) }
    }

    fun deleteMessage(resourceId: String) {
        messageRepository.deleteMessage(resourceId = ResourceId(resourceId))
    }
}