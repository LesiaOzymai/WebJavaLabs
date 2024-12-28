package com.example.spacecatsmarket.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.http.ProblemDetail;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private ProblemDetail createProblemDetails(HttpStatus status, String type, String title, String detail, String path) throws URISyntaxException {
        ProblemDetail problemDetail = ProblemDetail.forStatus(status);
        problemDetail.setType(new URI(type));
        problemDetail.setTitle(title);
        problemDetail.setDetail(detail);
        problemDetail.setProperty("path", path);
        return problemDetail;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneralException(Exception ex, WebRequest request) throws URISyntaxException {
        logger.error("Unexpected error: ", ex);

        ProblemDetail problemDetail = createProblemDetails(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "https://example.com/internal-server-error", // URI format
                "Internal Server Error",
                "An unexpected error occurred. Please contact support if the issue persists.",
                request.getDescription(false)
        );

        return new ResponseEntity<>(problemDetail, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) throws URISyntaxException {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        ProblemDetail problemDetail = createProblemDetails(
                HttpStatus.BAD_REQUEST,
                "https://example.com/bad-request",
                "Bad Request",
                "Validation failed for object 'ProductDTO'",
                request.getDescription(false)
        );
        problemDetail.setProperty("errors", errors);

        logger.error("Validation error: {}", errors);

        return new ResponseEntity<>(problemDetail, HttpStatus.BAD_REQUEST);
    }
}

