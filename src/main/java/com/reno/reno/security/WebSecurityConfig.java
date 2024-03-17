package com.reno.reno.security;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.reno.reno.security.jwt.AuthEntryPointJwt;
import com.reno.reno.security.jwt.AuthTokenFilter;
import com.reno.reno.security.services.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
// @EnableGlobalMethodSecurity(prePostEnabled = true)
// @EnableMethodSecurity // Spring Boot 3
@EnableMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig { // extends WebSecurityConfigurerAdapter {
  private @Autowired UserDetailsServiceImpl userDetailsService;

  private @Autowired AuthEntryPointJwt unauthorizedHandler;

  private @Autowired AuthTokenFilter authenticationJwtTokenFilter;

  private static final String[] AUTH_WHITELIST = {
      "/api/csrf**",
      "/api/auth/**",
      "/swagger-ui/**",
      "/v3/api-docs/**",
  };

  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

    authProvider.setUserDetailsService(userDetailsService);
    authProvider.setPasswordEncoder(passwordEncoder());

    return authProvider;
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
    return authConfig.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    // ***** CSRF Enable *****
    CookieCsrfTokenRepository tokenRepository = CookieCsrfTokenRepository.withHttpOnlyFalse();
    CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();
    // set the name of the attribute the CsrfToken will be populated on
    requestHandler.setCsrfRequestAttributeName("_csrf");
    http.cors().and()
        .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
        .addFilterBefore(authenticationJwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
        .csrf((csrf) -> csrf
            .csrfTokenRepository(tokenRepository)
            .csrfTokenRequestHandler(requestHandler))
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
        .authorizeHttpRequests()
        .requestMatchers(AUTH_WHITELIST).permitAll()
        .requestMatchers("/api/e-commerce-info/**").authenticated()
        .anyRequest().authenticated();

    http.authenticationProvider(authenticationProvider());

    // ***** CSRF Disable *****
    // http.cors().and().csrf().disable()
    // .authorizeHttpRequests()
    // .requestMatchers("/api/auth/**").permitAll()
    // .requestMatchers("/api/e-commerce-info/**").authenticated()
    // .anyRequest().authenticated()
    // .and()
    // .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
    // .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    // http.authenticationProvider(authenticationProvider());
    // http.addFilterBefore(authenticationJwtTokenFilter,
    // UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

  @Bean
  public CorsFilter corsFilter() {
    List<String> allowedMethods = Arrays.asList("GET", "PUT", "DELETE", "PATCH",
        "POST", "HEAD", "OPTIONS");
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowedOriginPatterns(Collections.singletonList("*"));
    config.setAllowedMethods(allowedMethods);
    config.setAllowCredentials(true);
    config.addAllowedHeader("*");
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", config);

    return new CorsFilter(source);
  }

}