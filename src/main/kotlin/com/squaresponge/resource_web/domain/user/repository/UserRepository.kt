package com.squaresponge.resource_web.domain.user.repository

import com.squaresponge.resource_web.domain.user.Account
import com.squaresponge.resource_web.domain.user.Invitation
import com.squaresponge.resource_web.domain.user.User
import com.squaresponge.resource_web.domain.user.UserId

interface UserRepository {
    fun findUser(userId: UserId): User

    fun findUser(invitation: Invitation): User

    fun findUser(account: Account): User

    fun save(user: User)
}