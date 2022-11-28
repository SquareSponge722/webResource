package com.squaresponge.resource_web.infrastructure.persistence.mybatis.mapper

import com.squaresponge.resource_web.infrastructure.persistence.mybatis.po.UserPo
import org.apache.ibatis.annotations.Mapper

@Mapper
interface UserMapper {
    fun findUser(
        id: Long? = null,
        name: String? = null,
        code: String? = null
    ): List<UserPo>

    fun saveUser(user: UserPo)

    fun updateUser(user: UserPo)
}