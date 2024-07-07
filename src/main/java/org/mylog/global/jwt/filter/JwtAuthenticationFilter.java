package org.mylog.global.jwt.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mylog.global.jwt.service.ReissueToken;
import org.mylog.global.jwt.token.JwtAuthenticationToken;
import org.mylog.global.jwt.util.JwtTokenizer;
import org.mylog.global.security.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenizer jwtTokenizer;
    private final ReissueToken reissueToken;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String token = getToken(request, "accessToken"); // accessToken 얻어냄

        if(StringUtils.hasText(token)){

            try{
                getAuthentication(token);

            }catch (ExpiredJwtException e){

                String accessToken = reissueToken.reissueAccessToken(response, getToken(request, "refreshToken"));

                if (!StringUtils.hasText(accessToken)) {
                    Cookie accessCookie = new Cookie("accessToken", "");
                    accessCookie.setMaxAge(0);
                    accessCookie.setPath("/");

                    response.addCookie(accessCookie);

                } else {
                    getAuthentication(accessToken);
                }

            }
        }
        filterChain.doFilter(request, response);
    }

    private String getToken(HttpServletRequest request, String token) {

        String authorization = request.getHeader("Authorization");
        if (StringUtils.hasText(authorization) && authorization.startsWith("Bearer ")) {
            return authorization.substring(7);
        }

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (token.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }

        return null;
    }

    private void getAuthentication(String token){
        Claims claims = jwtTokenizer.parseAccessToken(token);
        String email = claims.getSubject();

        Long userId = claims.get("userId", Long.class);
        String name = claims.get("name", String.class);
        String username = claims.get("username", String.class);
        List<GrantedAuthority> authorities = getGrantedAuthorities(claims);
        Long blogId = claims.get("blogId", Long.class);

        CustomUserDetails userDetails = new CustomUserDetails(userId, username,"",name,authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()), blogId);

        Authentication authentication = new JwtAuthenticationToken(authorities, userDetails, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private List<GrantedAuthority> getGrantedAuthorities(Claims claims){
        List<String> roles = (List<String>)claims.get("roles");
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String role : roles){
            authorities.add(()->role);
        }
        return authorities;
    }
}
