package com.nereuvitor.localeatsapi.security;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nereuvitor.localeatsapi.exceptions.StandardError;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LocalEatsAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
            AccessDeniedException accessDeniedException) throws IOException, ServletException {
        HttpStatusCode status = HttpStatus.FORBIDDEN;

        response.setStatus(status.value());
        response.setContentType("application/json;charset=UTF-8");

        StandardError err = new StandardError(
                System.currentTimeMillis(),
                status.value(),
                "Acesso Negado",
                "Você não tem permissão para acessar este recurso no LocalEats.",
                request.getRequestURI());

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(err);

        response.getWriter().append(json);
        response.getWriter().flush();
    }
}
