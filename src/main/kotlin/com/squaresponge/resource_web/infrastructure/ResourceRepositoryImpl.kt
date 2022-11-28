package com.squaresponge.resource_web.infrastructure

import com.squaresponge.resource_web.domain.resource.Resource
import com.squaresponge.resource_web.domain.resource.ResourceId
import com.squaresponge.resource_web.domain.resource.Title
import com.squaresponge.resource_web.domain.resource.repository.ResourceRepository
import com.squaresponge.resource_web.domain.user.UserId
import com.squaresponge.resource_web.infrastructure.persistence.mybatis.mapper.ResourceMapper
import com.squaresponge.resource_web.infrastructure.persistence.mybatis.po.ResourcePo
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class ResourceRepositoryImpl(private val resourceMapper: ResourceMapper) : ResourceRepository {
    override fun findResources(resourceId: ResourceId?, title: Title?, userId: UserId?): List<Resource> {
        val resources = resourceMapper.findResource(
            resourceId = resourceId?.resourceId?.toLong(),
            title = title?.text,
            userId = userId?.userId?.toLong()
        )
        return resources.map { it.toResource() }
    }

    @Transactional
    override fun saveResource(resource: Resource): ResourceId {
        val resourcePo = ResourcePo.ofResource(resource)
        if (resourcePo.id == null) {
            resourceMapper.saveResourceOnly(resourcePo)
            if (resourcePo.files.isNotEmpty()) {
                resourceMapper.saveResourceFileIds(resourcePo)
            }
        } else {
            resourceMapper.updateResourceOnly(resourcePo)
            resourceMapper.clearResourceFileIds(resourcePo.id)
            if (resourcePo.files.isNotEmpty()) {
                resourceMapper.saveResourceFileIds(resourcePo)
            }
        }
        return ResourceId(resourcePo.id?.toString())
    }

    @Transactional
    override fun deleteResource(resourceId: ResourceId) {
        val id = resourceId.resourceId!!.toLong()
        resourceMapper.clearResourceFileIds(id)
        resourceMapper.clearResourceOnly(id)
    }
}