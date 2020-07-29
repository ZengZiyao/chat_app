package com.zzy.chatapp.component;

import com.zzy.chatapp.common.utils.JwtTokenUtil;
import com.zzy.chatapp.service.AppUserDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private static final String TOKEN_HEADER = "Authorization";
    private static final String TOKEN_HEAD = "Bearer ";

    private final JwtTokenUtil jwtTokenUtil;
    private final AppUserDetailsService appUserDetailsService;

    public JwtAuthenticationTokenFilter(JwtTokenUtil jwtTokenUtil, AppUserDetailsService appUserDetailsService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.appUserDetailsService = appUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = httpServletRequest.getHeader(TOKEN_HEADER);

        if (authHeader != null && authHeader.startsWith(TOKEN_HEAD)) {
            String token = authHeader.substring(TOKEN_HEAD.length());
            String username = this.jwtTokenUtil.getUsernameFromToken(token);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
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
