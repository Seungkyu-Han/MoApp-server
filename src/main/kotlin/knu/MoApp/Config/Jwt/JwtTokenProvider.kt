package knu.MoApp.Config.Jwt

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component
import java.time.Duration
import java.util.*

@Component
class JwtTokenProvider {

    private final val accessTokenValidTime = Duration.ofDays(100).toMillis()

    fun getUserId(token: String, secretKey: String): Integer {
        return Jwts.parser()
            .setSigningKey(secretKey)
            .parseClaimsJws(token)
            .body
            .get("userId", java.lang.Integer::class.java)
    }

    fun createAccessToken(userId: Int, secretKey: String): String{
        val claims = Jwts.claims()
        claims["userId"] = userId

        return Jwts.builder()
            .setHeaderParam("type", "access")
            .setClaims(claims)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + accessTokenValidTime))
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact()
    }
}