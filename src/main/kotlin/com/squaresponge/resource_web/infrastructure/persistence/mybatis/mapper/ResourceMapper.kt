package com.squaresponge.resource_web.infrastructure.persistence.mybatis.mapper

import com.squaresponge.resource_web.infrastructure.persistence.mybatis.po.ResourcePo
import org.apache.ibatis.annotations.Mapper

@Mapper
interface ResourceMapper {
    fun findResource(
        resourceId: Long? = null,
        title: String? = null,
        userId: Long? = null
    ): List<ResourcePo>

    fun saveResourceOnly(resource: ResourcePo)

    fun updateResourceOnly(resource: ResourcePo)

    fun clearResourceOnly(resourceId: Long)

    fun clearResourceFileIds(resourceId: Long)

    fun saveResourceFileIds(resource: ResourcePo)
}