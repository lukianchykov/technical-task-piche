package com.lukianchykov.сontroller.handler;

import java.util.List;
import java.util.stream.Collectors;

import com.lukianchykov.utils.ApiError;
import com.lukianchykov.сontroller.AccountController;
import com.lukianchykov.сontroller.AccountTransactionController;
import com.lukianchykov.сontroller.exception.PublicApiErrorResponse;
import com.lukianchykov.сontroller.exception.PublicApiErrorResponse.PublicApiError;
import com.lukianchykov.сontroller.exception.PublicApiErrorResponse.PublicApiValidationError;
import com.lukianchykov.сontroller.exception.ResourceAlreadyExistException;
import com.lukianchykov.сontroller.exception.ResourceNotFoundException;
import com.lukianchykov.сontroller.exception.ServiceValidationException;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import brave.Span;
import brave.Tracer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import static com.lukianchykov.utils.Constants.API_ERROR_INTERNAL_ERROR_MESSAGE;

@ControllerAdvice(assignableTypes = {AccountController.class, AccountTransactionController.class})
@Slf4j
public class ControllerExceptionHandler {

    @Autowired
    private Tracer tracer;

    @ExceptionHandler(ValidationException.class)
    protected ResponseEntity<ApiError> handleError(ValidationException ex, WebRequest request) {
        log.error(ex.toString(), ex);
        ApiError apiError = new ApiError(ex);
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceAlreadyExistException.class)
    protected ResponseEntity<ApiError> handleError(ResourceAlreadyExistException ex, WebRequest request) {
        log.error(ex.toString(), ex);
        ApiError apiError = new ApiError(ex);
        return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<ApiError> handleError(ResourceNotFoundException ex, WebRequest request) {
        log.error(ex.toString(), ex);
        ApiError apiError = new ApiError(ex);
        apiError.getBody().put("missing_resource", ex.getResourceIdentifier());
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<PublicApiErrorResponse> handleError(RuntimeException ex) {
        log.error(ex.toString(), ex);
        var apiError = new PublicApiError(API_ERROR_INTERNAL_ERROR_MESSAGE);
        var publicApiErrorResponse = new PublicApiErrorResponse(getRequestId(), List.of(apiError));
        return new ResponseEntity<>(publicApiErrorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ServiceValidationException.class)
    protected ResponseEntity<PublicApiErrorResponse> handleError(ServiceValidationException ex) {
        log.error(ex.toString(), ex);
        List<PublicApiError> apiErrors = ex.getViolations().stream()
            .map(violation -> new PublicApiValidationError(violation.getMessage(), violation.getProperty()))
            .collect(Collectors.toList());
        var publicApiErrorResponse = new PublicApiErrorResponse(getRequestId(), apiErrors);
        return new ResponseEntity<>(publicApiErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalStateException.class)
    protected ResponseEntity<ApiError> handleError(IllegalStateException ex, WebRequest request) {
        log.error(ex.toString(), ex);
        ApiError apiError = new ApiError(ex);
        return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        log.error("Internal server error: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }

    private String getRequestId() {
        Span currentSpan = tracer.currentSpan();
        return currentSpan != null ? currentSpan.context().traceIdString() : null;
    }
}
