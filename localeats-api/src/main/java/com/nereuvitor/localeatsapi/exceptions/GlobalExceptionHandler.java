package com.nereuvitor.localeatsapi.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.nereuvitor.localeatsapi.services.exceptions.DataBaseException;
import com.nereuvitor.localeatsapi.services.exceptions.ObjectNotFoundException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> validation(MethodArgumentNotValidException e, HttpServletRequest request) {
        HttpStatusCode status = HttpStatus.UNPROCESSABLE_CONTENT;

        StandardError err = new StandardError(
                System.currentTimeMillis(),
                status.value(),
                "Erro de Validação",
                "Dados Inválidos",
                request.getRequestURI());

        for (FieldError field : e.getBindingResult().getFieldErrors()) {
            err.getErrors().add(new ValidationError(
                    field.getField(),
                    field.getDefaultMessage()));
        }

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<StandardError> constraintViolation(ConstraintViolationException e,
            HttpServletRequest request) {
        HttpStatusCode status = HttpStatus.UNPROCESSABLE_CONTENT;

        StandardError err = new StandardError(
                System.currentTimeMillis(),
                status.value(),
                "Erro de Validação",
                "Um campo ou mais não atendem às regras do LocalEats.",
                request.getRequestURI());

        for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
            String fieldName = violation.getPropertyPath().toString();
            String message = violation.getMessage();
            err.getErrors().add(new ValidationError(
                    fieldName,
                    message));
        }

        return ResponseEntity.status(status).body(err);
    }    

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<StandardError> dataIntegrity(DataIntegrityViolationException e, HttpServletRequest request) {
        HttpStatusCode status = HttpStatus.CONFLICT;

        StandardError err = new StandardError(
                System.currentTimeMillis(),
                status.value(),
                "Violação de Integridade",
                "Não é possível realizar está operação. O e-mail ou dados informados já estão cadastrados no LocalEats.",
                request.getRequestURI());

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(DataBaseException.class)
    public ResponseEntity<StandardError> dataBase(DataBaseException e, HttpServletRequest request) {
        HttpStatusCode status = HttpStatus.BAD_REQUEST;

        StandardError err = new StandardError(
                System.currentTimeMillis(),
                status.value(),
                "Erro de Integridade",
                e.getMessage(),
                request.getRequestURI());

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request) {
        HttpStatusCode status = HttpStatus.NOT_FOUND;

        StandardError err = new StandardError(
                System.currentTimeMillis(),
                status.value(),
                "Não Encontrado",
                e.getMessage(),
                request.getRequestURI());

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardError> handleGenericException(Exception e, HttpServletRequest request) {
        HttpStatusCode status = HttpStatus.INTERNAL_SERVER_ERROR;

        StandardError err = new StandardError(
                System.currentTimeMillis(),
                status.value(),
                "Erro Interno",
                "Ocorreu um erro inesperado no LocalEats. Tente novamente mais tarde.",
                request.getRequestURI());

        e.printStackTrace();

        return ResponseEntity.status(status).body(err);
    }

}
