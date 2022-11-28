package com.squaresponge.resource_web.interfaces.message_manage

import com.squaresponge.resource_web.application.MessageService
import com.squaresponge.resource_web.interfaces.message_manage.dto.MessageDetailInfo
import com.squaresponge.resource_web.interfaces.message_manage.dto.MessageInfo
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/message")
class MessageController(
    private val messageService: MessageService
) {
    @PostMapping("/post")
    fun postMessage(
        messageInfo: MessageInfo,
        authentication: Authentication
    ) {
        val username = authentication.name
        messageService.postMessage(username, messageInfo)
    }

    @GetMapping("/find")
    fun findMessage(
        @RequestParam resourceId: String
    ): List<MessageDetailInfo> {
        return messageService.findMessages(resourceId)
    }
}