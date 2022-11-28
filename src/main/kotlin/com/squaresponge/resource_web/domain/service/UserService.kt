package com.squaresponge.resource_web.domain.service

import com.squaresponge.resource_web.domain.user.Account
import com.squaresponge.resource_web.domain.user.User

interface UserService {
    fun loginWithAccount(user: User, account: Account)
}