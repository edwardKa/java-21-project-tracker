package com.company.project.tracker.handlers;

import com.company.project.tracker.exception.AuthenticationException;
import com.company.project.tracker.model.entity.User;
import com.company.project.tracker.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class SecurityInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private AuthenticationService authenticationService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        /*
        * "/v2/api-docs",
                        "/swagger-resources/**",
                        "/configuration/**",
                        "/swagger-ui.html",
                        "/webjars/**",
        * */
        if (request.getRequestURI().contains("login") || request.getRequestURI().contains("/v2/")
                ||request.getRequestURI().contains("swagger") ||
                request.getRequestURI().contains("configuration") ||
                request.getRequestURI().contains("webjars") ||
                request.getRequestURI().contains("registration")) {
            return true;
        }

        String session = request.getHeader("Authorization");
        if (session == null) {
            throw new AuthenticationException("User is not authenticated");
        }

        User authenticatedUser = authenticationService.getAuthenticatedUser(session);
        if (authenticatedUser == null) {
            throw new AuthenticationException("User is not authenticated");
        }

        return super.preHandle(request, response, handler);
    }
}
