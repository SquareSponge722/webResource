package com.squaresponge.resource_web.infrastructure

import com.squaresponge.resource_web.domain.service.UserService
import com.squaresponge.resource_web.domain.user.Account
import com.squaresponge.resource_web.domain.user.User
import com.squaresponge.resource_web.domain.user.exception.PasswordIncorrectException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(private val passwordEncoder: PasswordEncoder) : UserService {
    override fun loginWithAccount(user: User, account: Account) {
        if (!passwordEncoder.matches(account.password.passwordRaw, user.account.password.passwordEncoded)) {
            throw PasswordIncorrectException(account.password.toString())
        }
        user.login()
    }
}