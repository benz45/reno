package com.reno.reno.security.jwt;

import java.io.IOException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.reno.reno.model.auth.RefreshTokenEntity;
import com.reno.reno.security.services.RefreshTokenService;
import com.reno.reno.security.services.UserDetailsServiceImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthTokenFilter extends OncePerRequestFilter {

  private @Autowired JwtUtils jwtUtils;

  private @Autowired UserDetailsServiceImpl userDetailsService;

  private @Autowired RefreshTokenService refreshTokenService;

  private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    try {
      String jwt = parseJwt(request);
      String rft = parseRft(request);
      Optional<RefreshTokenEntity> refreshTokenOpt = refreshTokenService.findByToken(rft);
      RefreshTokenEntity refreshToken = null;
      if (refreshTokenOpt.isPresent()) {
        refreshToken = refreshTokenService.verifyExpiration(refreshTokenOpt.get());
      }
      if (jwt != null && jwtUtils.validateJwtToken(jwt) && refreshToken != null) {
        String username = jwtUtils.getUserNameFromJwtToken(jwt);

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
            userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    } catch (Exception e) {
      logger.error("Cannot set user authentication: {}", e);
    }

    filterChain.doFilter(request, response);
  }

  private String parseJwt(HttpServletRequest request) {
    String headerAuth = request.getHeader("Authorization");

    if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
      return headerAuth.substring(7, headerAuth.length());
    }

    return null;
  }

  private String parseRft(HttpServletRequest request) {
    String headerRft = request.getHeader("RefreshToken");
    if (StringUtils.hasText(headerRft)) {
      return headerRft;
    }

    return null;
  }

}
