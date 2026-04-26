package com.nereuvitor.localeatsapi.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.nereuvitor.localeatsapi.services.exceptions.ObjectNotFoundException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> validation(MethodArgumentNotValidException e, HttpServletRequest request) {
        HttpStatusCode status = HttpStatus.UNPROCESSABLE_CONTENT;

        StandardError error = new StandardError(
                System.currentTimeMillis(),
                status.value(),
                "Erro de Validação",
                "Dados Inválidos",
                request.getRequestURI());

        for (FieldError field : e.getBindingResult().getFieldErrors()) {
            error.getErrors().add(new ValidationError(
                    field.getField(),
                    field.getDefaultMessage()));
        }

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<StandardError> constraintViolation(ConstraintViolationException e,
            HttpServletRequest request) {
        HttpStatusCode status = HttpStatus.UNPROCESSABLE_CONTENT;

        StandardError error = new StandardError(
                System.currentTimeMillis(),
                status.value(),
                "Erro de Validação",
                "Um campo ou mais não atendem às regras do LocalEats.",
                request.getRequestURI());

        for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
            String fieldName = violation.getPropertyPath().toString();
            String message = violation.getMessage();
            error.getErrors().add(new ValidationError(
                    fieldName,
                    message));
        }

        return ResponseEntity.status(status).body(error);
    }    

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<StandardError> dataIntegrity(DataIntegrityViolationException e, HttpServletRequest request) {
        HttpStatusCode status = HttpStatus.CONFLICT;

        StandardError error = new StandardError(
                System.currentTimeMillis(),
                status.value(),
                "Violação de Integridade",
                "Não é possível realizar está operação. O dado enviado já existe ou possui vinculos ativos no LocalEats.",
                request.getRequestURI());

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request) {
        HttpStatusCode status = HttpStatus.NOT_FOUND;

        StandardError error = new StandardError(
                System.currentTimeMillis(),
                status.value(),
                "Não Encontrado",
                e.getMessage(),
                request.getRequestURI());

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardError> handleGenericException(Exception e, HttpServletRequest request) {
        HttpStatusCode status = HttpStatus.INTERNAL_SERVER_ERROR;

        StandardError error = new StandardError(
                System.currentTimeMillis(),
                status.value(),
                "Erro Interno",
                "Ocorreu um erro inesperado no LocalEats. Tente novamente mais tarde.",
                request.getRequestURI());

        e.printStackTrace();

        return ResponseEntity.status(status).body(error);
    }

}
