package com.squaresponge.resource_web.domain.user

import com.squaresponge.resource_web.domain.shared.ValueObject

data class Point(val point: Int = 0) : ValueObject<Point> {

    fun canReduce(point: Point): Boolean {
        return this.point >= point.point
    }

    operator fun plus(other: Point) = Point(this.point + other.point)
}
