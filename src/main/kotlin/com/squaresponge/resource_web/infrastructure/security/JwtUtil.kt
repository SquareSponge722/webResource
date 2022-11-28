package com.squaresponge.resource_web.infrastructure.security

import org.springframework.security.oauth2.jwt.JwtClaimsSet
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.JwtEncoderParameters
import org.springframework.stereotype.Component
import java.time.Instant

@Component
class JwtUtil(private val jwtEncoder: JwtEncoder) {
    fun issueToken(name: String, authority: String, expiry: Long = 36000L): String {
        val now = Instant.now()
        val claimsSet = JwtClaimsSet.builder()
            .issuer("self")
            .issuedAt(now)
            .expiresAt(now.plusSeconds(expiry))
            .subject(name)
            .claim("scope", authority)
            .build()
        return jwtEncoder.encode(JwtEncoderParameters.from(claimsSet)).tokenValue
    }
}