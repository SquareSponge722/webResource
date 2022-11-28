package com.squaresponge.resource_web.domain.shared

interface DomainEvent<T> {
    fun sameEventAs(other: T): Boolean
}