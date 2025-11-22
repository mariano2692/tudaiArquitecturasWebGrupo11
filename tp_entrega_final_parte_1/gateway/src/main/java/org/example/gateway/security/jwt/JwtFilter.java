package org.example.gateway.security.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;

public class JwtFilter extends OncePerRequestFilter {
    private final Logger log = LoggerFactory.getLogger(JwtFilter.class);

    public static final String AUTHORIZATION_HEADER = "Authorization";

    public final TokenProvider tokenProvider;

    public JwtFilter (TokenProvider tokenProvider){
        this.tokenProvider = tokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = resolveToken(request);
        try{
            if(StringUtils.hasText(jwt) && this.tokenProvider.validateToken(jwt)){
                Authentication authentication = this.tokenProvider.getAuthentication(jwt);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (ExpiredJwtException e) {
            log.info("REST Request UNAUTHORIZED - La sesión ha expirado.");
            response.setStatus(498);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write(new JwtErrorDTO().toJson());
            return;
        }
        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private static class JwtErrorDTO {
        private final String message = "Token expired";
        private final String date = LocalDateTime.now().toString();

        public JwtErrorDTO() {}

        public String toJson() {
            try{
                return new ObjectMapper().writeValueAsString(this);
            }
            catch (RuntimeException | JsonProcessingException e) {
                return String.format("{message: %s}", this.message);
            }

        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();

        // Endpoints públicos
        return path.equals("/api/authenticate") ||
                (path.equals("/api/users") && request.getMethod().equals("POST"));
    }
}
