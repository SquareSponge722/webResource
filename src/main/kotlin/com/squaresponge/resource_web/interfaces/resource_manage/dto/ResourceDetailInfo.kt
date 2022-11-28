package com.squaresponge.resource_web.interfaces.resource_manage.dto

import com.squaresponge.resource_web.domain.resource.Resource

data class ResourceDetailInfo(
    val id: String,
    val title: String,
    val description: String,
    val uploader: String,
    val files: List<String>
) {
    companion object {
        fun ofResource(resource: Resource) = ResourceDetailInfo(
            id = resource.resourceId.resourceId!!,
            title = resource.profile.title.text,
            description = resource.profile.introduction,
            uploader = resource.userId.userId,
            files = resource.document.files.map { it.fileId }
        )
    }
}