package com.squaresponge.resource_web.domain.shared

interface Entity<T> {
    fun sameIdentityAs(other: T): Boolean
}