package com.squaresponge.resource_web.interfaces.user_manage.dto

import com.squaresponge.resource_web.domain.user.Account
import com.squaresponge.resource_web.domain.user.Invitation
import com.squaresponge.resource_web.domain.user.Password
import com.squaresponge.resource_web.domain.user.UserId

data class RegisterInfo(
    val name: String,
    val password: String,
    val code: String
) {

    fun toAccount() = Account(name, Password.raw(password))

    fun toInvitation() = Invitation(code, UserId())
}
