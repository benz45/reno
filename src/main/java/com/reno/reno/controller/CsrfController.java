package com.reno.reno.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reno.reno.security.jwt.AuthEntryPointJwt;

import jakarta.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/csrf")
public class CsrfController {
    @Autowired
    private HttpServletRequest request;

    private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

    @GetMapping("/firsttime")
    public CsrfToken allAccess(CsrfToken token) {
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }
        logger.info("[" + ipAddress + "]: Generate csrf token: " + token.getToken());
        return token;
    }
}
