package com.squaresponge.resource_web.infrastructure.persistence.mybatis.mapper

import com.squaresponge.resource_web.infrastructure.persistence.mybatis.po.MessagePo
import org.apache.ibatis.annotations.Mapper

@Mapper
interface MessageMapper {
    fun findMessage(
        id: Long? = null,
        userId: Long? = null,
        resourceId: Long? = null
    ): List<MessagePo>

    fun saveMessage(message: MessagePo)

    fun deleteMessage(
        id: Long? = null,
        resourceId: Long? = null
    )

    fun generateNextIndex(resourceId: Long): Int?
}