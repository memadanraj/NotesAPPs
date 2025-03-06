package com.notesAPP.NotesAPP.Filters;

import com.notesAPP.NotesAPP.Impl.JWTService;
import com.notesAPP.NotesAPP.Impl.UserdetailsService;
import io.jsonwebtoken.JwtException; // Import JwtException
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTSecurityFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JWTSecurityFilter.class);

    @Autowired
    private JWTService jwtService;

    @Autowired
    private ApplicationContext context;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String userName = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);

            if (token == null || token.isEmpty()) {
                logger.warn("JWT token is null or empty.");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // or SC_FORBIDDEN
                return; // Stop processing the filter chain
            }

            try {
                userName = jwtService.extractUserName(token);
            } catch (JwtException e) {
                logger.warn("Invalid JWT token: " + e.getMessage());
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return; // Stop processing the filter chain
            }
        } else {
            logger.debug("Authorization header is missing or invalid.");
        }

        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = context.getBean(UserdetailsService.class).loadUserByUsername(userName);
            if (jwtService.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            } else {
                logger.warn("JWT token validation failed for user: " + userName);
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}