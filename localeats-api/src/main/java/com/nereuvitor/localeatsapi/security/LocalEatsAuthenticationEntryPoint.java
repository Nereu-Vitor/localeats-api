package com.nereuvitor.localeatsapi.security;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nereuvitor.localeatsapi.exceptions.StandardError;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LocalEatsAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
        HttpStatusCode status = HttpStatus.FORBIDDEN;

        response.setStatus(status.value());
        response.setContentType("application/json;charset=UTF-8");

        StandardError err = new StandardError(
                System.currentTimeMillis(),
                status.value(),
                "Acesso Negado",
                "Sua sessão expirou ou você não está conectado. Por favor, faça login novamente.",
                request.getRequestURI());

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(err);

        response.getWriter().append(json);
        response.getWriter().flush();
    }

}
