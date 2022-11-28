package com.squaresponge.resource_web.infrastructure.persistence.mybatis.po

import com.squaresponge.resource_web.domain.resource.*
import com.squaresponge.resource_web.domain.user.UserId

data class ResourcePo(
    val id: Long? = null,
    val name: String,
    val description: String,
    val userId: Long,
    val files: MutableList<String> = mutableListOf()
) {

    /**
     * for mybatis
     */
    constructor(
        id: Long?,
        name: String,
        description: String,
        userId: Long,
        file: String?
    ) : this(
        id,
        name,
        description,
        userId,
        if (file == null) mutableListOf()
        else mutableListOf(file)
    )

    companion object {
        fun ofResource(resource: Resource) = ResourcePo(
            id = resource.resourceId.resourceId?.toLong(),
            name = resource.profile.title.text,
            description = resource.profile.introduction,
            userId = resource.userId.userId.toLong(),
            files = resource.document.files.map { it.fileId }.toMutableList()
        )
    }

    fun toResource() = Resource(
        resourceId = ResourceId(id!!.toString()),
        profile = Profile(
            title = Title(name),
            introduction = description
        ),
        document = Document(
            files = files.map { File(it) }
        ),
        userId = UserId(userId.toString())
    )
}
