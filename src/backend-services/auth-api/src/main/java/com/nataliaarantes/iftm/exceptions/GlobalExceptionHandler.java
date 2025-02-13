package com.nataliaarantes.iftm.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(HttpClientErrorException.class)
  public ResponseEntity<Map<String, Object>> handleClientError(HttpClientErrorException ex) {
    return buildErrorResponse(ex.getStatusCode(), ex.getMessage());
  }

  @ExceptionHandler(HttpServerErrorException.class)
  public ResponseEntity<Map<String, Object>> handleServerError(HttpServerErrorException ex) {
    return buildErrorResponse(ex.getStatusCode(), ex.getMessage());
  }

  private ResponseEntity<Map<String, Object>> buildErrorResponse(HttpStatusCode status, String message) {
    Map<String, Object> errorDetails = new LinkedHashMap<>();
    errorDetails.put("timestamp", LocalDateTime.now());
    errorDetails.put("status", status.value());
    errorDetails.put("message", message);

    return new ResponseEntity<>(errorDetails, status);
  }
}

