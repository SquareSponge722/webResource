package com.squaresponge.resource_web.interfaces.resource_manage

import com.squaresponge.resource_web.application.ResourceService
import com.squaresponge.resource_web.domain.resource.ResourceId
import com.squaresponge.resource_web.domain.resource.Title
import com.squaresponge.resource_web.domain.resource.repository.ResourceRepository
import com.squaresponge.resource_web.domain.user.Account
import com.squaresponge.resource_web.domain.user.Password
import com.squaresponge.resource_web.domain.user.Point
import com.squaresponge.resource_web.domain.user.UserId
import com.squaresponge.resource_web.domain.user.repository.UserRepository
import com.squaresponge.resource_web.interfaces.resource_manage.dto.ResourceDetailInfo
import com.squaresponge.resource_web.interfaces.resource_manage.dto.ResourceInfo
import org.slf4j.LoggerFactory
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/resource")
class ResourceController(
    private val resourceService: ResourceService,
    private val resourceRepository: ResourceRepository
) {

    private val logger = LoggerFactory.getLogger(ResourceController::class.java)

    @PostMapping("/create")
    fun createResource(
        resourceInfo: ResourceInfo,
        authentication: JwtAuthenticationToken
    ): ResourceId {
        val username = authentication.token.subject
        return resourceService.saveResource(
            username,
            resourceInfo
        ) { user, _ -> user.collectPoint(Point(10)) }
    }

    @PostMapping("/modify")
    fun modifyResource(
        resourceInfo: ResourceInfo,
        authentication: JwtAuthenticationToken
    ) {
        val username = authentication.token.subject
        resourceService.saveResource(username, resourceInfo)
    }

    @GetMapping("/search")
    fun searchResource(
        @RequestParam id: String? = null,
        @RequestParam title: String? = null,
        @RequestParam userId: String? = null
    ): List<ResourceDetailInfo> {
        return resourceRepository.findResources(
            resourceId = id?.let { ResourceId(it) },
            title = title?.let { Title("%$it%") },
            userId = userId?.let { UserId(it) }
        ).map { ResourceDetailInfo.ofResource(it) }
    }

    @DeleteMapping("/delete/{id}")
    fun deleteResource(
        @PathVariable id: String,
        authentication: JwtAuthenticationToken
    ) {
        val username = authentication.token.subject
        resourceService.deleteResource(username, id)
    }

    @PostMapping("/upload/{id}")
    fun uploadFile(
        @PathVariable id: String,
        @RequestPart file: MultipartFile,
        authentication: Authentication
    ) {
        resourceService.uploadFile(
            username = authentication.name,
            multipartFile = file,
            resourceId = id
        )
    }

    @GetMapping("/download/{id}")
    fun downloadFile(
        @PathVariable id: String,
        authentication: Authentication
    ) = resourceService.downloadFile(
        username = authentication.name,
        fileId = id
    )
}