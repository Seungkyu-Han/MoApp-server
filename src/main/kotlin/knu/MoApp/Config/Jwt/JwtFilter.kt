package knu.MoApp.Config.Jwt

import lombok.RequiredArgsConstructor
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@RequiredArgsConstructor
@Component
class JwtFilter(
    @Value("{jwt.secret.key}")
    val secretKey: String,
    val jwtTokenProvider: JwtTokenProvider
):OncePerRequestFilter() {


    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authorization = request.getHeader(org.springframework.http.HttpHeaders.AUTHORIZATION)

        if(authorization == null || !authorization.startsWith("Bearer ")){
            filterChain.doFilter(request, response)
            return
        }

        val userId = jwtTokenProvider.getUserId(authorization.split(" ")[1], secretKey)

        val usernamePasswordAuthenticationFilter = UsernamePasswordAuthenticationToken(userId, null, listOf(SimpleGrantedAuthority("USER")))
        SecurityContextHolder.getContext().authentication = usernamePasswordAuthenticationFilter
        filterChain.doFilter(request, response)



    }


}