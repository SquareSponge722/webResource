package com.squaresponge.resource_web.domain.message

import com.squaresponge.resource_web.domain.resource.ResourceId
import com.squaresponge.resource_web.domain.shared.ValueObject
import com.squaresponge.resource_web.domain.user.UserId
import java.sql.Date

data class Meta(
    val from: UserId,
    val resourceId: ResourceId,
    val index: Int,
    val date: Date
) : ValueObject<Meta>
