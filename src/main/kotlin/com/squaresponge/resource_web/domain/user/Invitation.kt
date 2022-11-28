package com.squaresponge.resource_web.domain.user

import com.squaresponge.resource_web.domain.shared.ValueObject
import org.apache.tomcat.util.security.MD5Encoder
import org.slf4j.LoggerFactory
import org.springframework.util.DigestUtils
import java.util.UUID
import kotlin.math.log

data class Invitation(
    val code: String,
    val invitor: UserId
) : ValueObject<Invitation> {

    companion object {
        fun generate(invitorId: UserId): Invitation {
            val uuid = UUID.randomUUID().toString()
            val md5 = DigestUtils.md5DigestAsHex(uuid.toByteArray())
            return Invitation(md5, invitorId)
        }
    }
}