package com.squaresponge.resource_web.application

import com.squaresponge.resource_web.domain.resource.Document
import com.squaresponge.resource_web.domain.resource.File
import com.squaresponge.resource_web.domain.resource.Resource
import com.squaresponge.resource_web.domain.resource.ResourceId
import com.squaresponge.resource_web.domain.resource.repository.ResourceRepository
import com.squaresponge.resource_web.domain.user.Account
import com.squaresponge.resource_web.domain.user.Identity
import com.squaresponge.resource_web.domain.user.User
import com.squaresponge.resource_web.domain.user.repository.UserRepository
import com.squaresponge.resource_web.infrastructure.persistence.mongodb.MongoFileService
import com.squaresponge.resource_web.interfaces.resource_manage.dto.ResourceInfo
import org.apache.ibatis.javassist.NotFoundException
import org.springframework.core.io.ByteArrayResource
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
class ResourceService(
    private val userRepository: UserRepository,
    private val resourceRepository: ResourceRepository,
    private val messageService: MessageService,
    private val mongoFileService: MongoFileService
) {
    @Transactional
    fun saveResource(
        username: String,
        resourceInfo: ResourceInfo,
        beforeSave: ((user: User, resource: Resource) -> Unit)? = null
    ): ResourceId {
        val user = userRepository.findUser(Account(username))
        val resource = resourceInfo.toResource(user)
        beforeSave?.invoke(user, resource)

        val resourceId = resourceRepository.saveResource(resource)
        userRepository.save(user)
        return resourceId
    }

    @Transactional
    fun deleteResource(
        username: String,
        resourceId: String
    ) {
        val user = userRepository.findUser(Account(username))
        val resource = resourceRepository.findResources(resourceId = ResourceId(resourceId)).firstOrNull()
            ?: throw NotFoundException("$resourceId not found.")

        if (user.identity == Identity.ADMIN || resource.userId == user.userId) {
            messageService.deleteMessage(resource.resourceId.resourceId!!)
            resourceRepository.deleteResource(resource.resourceId)
            mongoFileService.deleteFile(resource.resourceId.resourceId)
        } else {
            throw SecurityException(
                "$username has no permission.\n" +
                        "only uploader and admin can delete resources."
            )
        }
    }

    fun uploadFile(
        username: String,
        resourceId: String,
        multipartFile: MultipartFile
    ) {
        val user = userRepository.findUser(Account(username))
        val resource = resourceRepository.findResources(resourceId = ResourceId(resourceId)).firstOrNull()
            ?: throw NotFoundException("$resourceId not found.")

        if (user.identity == Identity.ADMIN || resource.userId == user.userId) {
            val meta = mapOf(
                "resourceId" to resourceId,
                "remove" to false
            )
            val fileId = mongoFileService.saveFile(multipartFile, meta)
            val files = resource.document.files.toMutableList()
            files.add(File(fileId))
            resource.document = Document(files)
            resourceRepository.saveResource(resource)
        } else {
            throw SecurityException(
                "$username has no permission.\n" +
                        "only uploader and admin can upload files."
            )
        }
    }

    @Transactional
    fun downloadFile(
        username: String,
        fileId: String
    ): ByteArrayResource {
        val user = userRepository.findUser(Account(username))
        user.download()
        userRepository.save(user)

        val file = mongoFileService.getFile(fileId)
        return ByteArrayResource(file.inputStream.readAllBytes())
    }
}