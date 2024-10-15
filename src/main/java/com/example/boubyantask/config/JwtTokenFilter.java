package com.example.boubyantask.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtDecoder jwtDecoder;

    public JwtTokenFilter(JwtDecoder jwtDecoder) {
        this.jwtDecoder = jwtDecoder;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = extractTokenFromRequest(request);

        if (token != null) {
            try {
                // Decode the token
                Jwt jwt = jwtDecoder.decode(token);

                // Check the token type
                String tokenType = jwt.getClaimAsString("type");

                // If it's a refresh token, deny access
                if ("refresh_token".equals(tokenType) && !request.getRequestURI().equals("/api/v1/refreshToken")) {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.getWriter().write("Refresh token cannot be used to access APIs.");
                    return;
                }
                if (!"refresh_token".equals(tokenType) && request.getRequestURI().equals("/api/v1/refreshToken")) {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.getWriter().write("Access token cannot be used as refresh token");
                    return;
                }

                // Otherwise, proceed with the authentication process
                SecurityContextHolder.getContext().setAuthentication(getAuthentication(jwt));
            } catch (Exception e) {
                // Invalid token, handle the error
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Invalid or expired token.");
                return;
            }
        }

        // Continue the filter chain if no issues
        filterChain.doFilter(request, response);
    }

    // Extract token from request headers
    private String extractTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    // Optional: Authentication method to map the JWT to an Authentication object
    private Authentication getAuthentication(Jwt jwt) {
        // Implement this based on your system (optional)
        // For example, extract user details and roles from the JWT and return
        return null;
    }
}
