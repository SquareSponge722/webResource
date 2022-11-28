package com.squaresponge.resource_web.infrastructure

import com.squaresponge.resource_web.domain.user.Account
import com.squaresponge.resource_web.domain.user.Invitation
import com.squaresponge.resource_web.domain.user.User
import com.squaresponge.resource_web.domain.user.UserId
import com.squaresponge.resource_web.domain.user.repository.UserRepository
import com.squaresponge.resource_web.infrastructure.persistence.mybatis.mapper.UserMapper
import com.squaresponge.resource_web.infrastructure.persistence.mybatis.po.UserPo
import org.apache.ibatis.javassist.NotFoundException
import org.slf4j.LoggerFactory
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Repository
import java.sql.Date
import java.time.LocalDate

@Repository
class UserRepositoryImpl(
    private val userMapper: UserMapper,
    private val encoder: PasswordEncoder
) : UserRepository {

    private val logger = LoggerFactory.getLogger(UserRepositoryImpl::class.java)

    override fun findUser(userId: UserId): User {
        val userPo = userMapper.findUser(id = userId.userId.toLong()).firstOrNull()
            ?: throw NotFoundException("user not found by $userId")
        return userPo.toUser()
    }

    override fun findUser(invitation: Invitation): User {
        val userPo = userMapper.findUser(code = invitation.code).firstOrNull()
            ?: throw NotFoundException("user not found by $invitation")
        return userPo.toUser()
    }

    override fun findUser(account: Account): User {
        val userPo = userMapper.findUser(name = account.name).firstOrNull()
            ?: throw NotFoundException("user not found by $account")
        return userPo.toUser()
    }

    override fun save(user: User) {
        var userPo = UserPo.ofUser(user)
        if (userPo.id == UserPo.NO_ID) {
            userPo = userPo.copy(password = encoder.encode(userPo.password))
            userMapper.saveUser(userPo)
        } else {
            userMapper.updateUser(userPo)
        }
        logger.info(userPo.toString())
    }
}