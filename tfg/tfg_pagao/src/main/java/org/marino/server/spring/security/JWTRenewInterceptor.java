package org.marino.server.spring.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Log4j2
@Component
public class JWTRenewInterceptor implements HandlerInterceptor {

    private final ServicesJwt servicesJwt;

    public JWTRenewInterceptor(ServicesJwt servicesJwt) {
        this.servicesJwt = servicesJwt;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            try {
                String email = servicesJwt.extractEmail(token);
                if (email != null && servicesJwt.validateToken(token, email)) {
                    String newToken = servicesJwt.generateToken(email);
                    response.setHeader("Authorization", "Bearer " + newToken);
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }

}
