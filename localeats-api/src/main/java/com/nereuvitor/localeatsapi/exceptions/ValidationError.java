package com.nereuvitor.localeatsapi.exceptions;

public record ValidationError(String field, String message) {

}
