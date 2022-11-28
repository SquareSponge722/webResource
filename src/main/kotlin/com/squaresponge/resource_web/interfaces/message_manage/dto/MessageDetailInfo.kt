package com.squaresponge.resource_web.interfaces.message_manage.dto

import com.squaresponge.resource_web.domain.message.Message
import java.sql.Date

data class MessageDetailInfo(
    val id: String,
    val userId: String,
    val text: String,
    val index: Int,
    val post: Date
) {
    companion object {
        fun ofMessage(message: Message) = MessageDetailInfo(
            id = message.messageId.id!!,
            userId = message.meta.from.userId,
            text = message.content.text,
            index = message.meta.index,
            post = message.meta.date
        )
    }
}