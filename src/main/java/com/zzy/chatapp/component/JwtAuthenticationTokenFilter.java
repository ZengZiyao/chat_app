package com.zzy.chatapp.component;

import com.zzy.chatapp.common.utils.JwtTokenUtil;
import com.zzy.chatapp.service.impl.UserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private static final String TOKEN_HEADER = "Authorization";
    private static final String TOKEN_HEAD = "Bearer ";

    private JwtTokenUtil jwtTokenUtil;
    private UserDetailsService appUserDetailsService;
    private RedisTemplate<String, String> redisTemplate;

    public JwtAuthenticationTokenFilter(JwtTokenUtil jwtTokenUtil, UserDetailsServiceImpl appUserDetailsService, RedisTemplate  redisTemplate) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.appUserDetailsService = appUserDetailsService;
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = httpServletRequest.getHeader(TOKEN_HEADER);

        if (authHeader != null && authHeader.startsWith(TOKEN_HEAD)) {
            String token = authHeader.substring(TOKEN_HEAD.length());
            String username = this.jwtTokenUtil.getUsernameFromToken(token);

            if (username != null && (redisTemplate.opsForValue().get(username) == null || !redisTemplate.opsForValue().get(username).equals(token)) && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.appUserDetailsService.loadUserByUsername(username);

                if (this.jwtTokenUtil.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }


            }
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);

    }
}
