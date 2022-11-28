package com.squaresponge.resource_web

import com.squaresponge.resource_web.domain.message.repository.MessageRepository
import com.squaresponge.resource_web.domain.resource.ResourceId
import com.squaresponge.resource_web.infrastructure.persistence.mybatis.mapper.MessageMapper
import com.squaresponge.resource_web.infrastructure.persistence.mybatis.mapper.ResourceMapper
import com.squaresponge.resource_web.infrastructure.persistence.mybatis.mapper.UserMapper
import com.squaresponge.resource_web.infrastructure.persistence.mybatis.po.MessagePo
import com.squaresponge.resource_web.infrastructure.persistence.mybatis.po.ResourcePo
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.CoroutinesUtils
import java.sql.Date
import java.time.LocalDate
import kotlin.coroutines.CoroutineContext

@SpringBootTest
class ResourceWebApplicationTests {

    @Autowired
    private lateinit var userMapper: UserMapper

    @Autowired
    private lateinit var resourceMapper: ResourceMapper

    @Autowired
    private lateinit var messageMapper: MessageMapper

    @Autowired
    private lateinit var messageRepository: MessageRepository

    @Test
    @Synchronized
    fun testMessage() {
        val index = messageRepository.generateNextIndex(ResourceId("1"))
        messageMapper.saveMessage(
            MessagePo(
                userId = 3,
                resourceId = 1,
                text = "message",
                index = index,
                post = Date.valueOf(LocalDate.now())
            )
        )
        println(messageRepository)
    }

    @Test
    fun testGenerateIndex() {
        object : Thread() {
            override fun run() {
                testMessage()
                sleep(2000)
            }
        }.start()
        object : Thread() {
            override fun run() {
                testMessage()
            }
        }.start()
        Thread.sleep(5000)
    }

}
