package com.nereuvitor.localeatsapi.security;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.nereuvitor.localeatsapi.exceptions.StandardError;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tools.jackson.databind.ObjectMapper;

public class JWTAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
        HttpStatusCode status = HttpStatus.UNAUTHORIZED;

        response.setStatus(status.value());
        response.setContentType("application/json;charset=UTF-8");

        StandardError error = new StandardError(
                System.currentTimeMillis(),
                status.value(),
                "Não autorizado",
                "E-mail ou senha inválidos",
                request.getRequestURI());

        String json = new ObjectMapper().writeValueAsString(error);

        response.getWriter().append(json);
    }
}
