package com.squaresponge.resource_web.domain.user.exception

import com.squaresponge.resource_web.domain.user.Point

class PointsShortageException(
    private val username: String,
    private val point: Point
) : Exception() {
    override val message: String
        get() = "$username have insufficient points $point to continue operation"
}