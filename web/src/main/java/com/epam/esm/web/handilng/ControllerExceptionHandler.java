package com.epam.esm.web.handilng;

import com.epam.esm.service.exception.EntityAlreadyExistsException;
import com.epam.esm.service.exception.EntityNotFoundException;
import com.epam.esm.service.exception.ValidationException;
import com.epam.esm.web.model.BindExceptionModel;
import com.epam.esm.web.model.ExceptionModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger LOGGER = LogManager.getLogger(ControllerExceptionHandler.class);
    private static final int INTERNAL_SERVER_ERROR = 500_01;
    private static final int ENTITY_NOT_FOUND_EXCEPTION = 404_01;
    private static final int ENTITY_EXISTS_EXCEPTION = 409_01;
    private static final int NOT_FOUND_EXCEPTION = 404_00;
    private static final int BAD_REQUEST = 400_00;
    private static final String INTERNAL_SERVER_ERROR_MESSAGE = "Internal server error";
    private static final String INVALID_FIELD = "invalid field: ";

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        LOGGER.error(ex.getMessage(), ex);
        List<FieldError> allErrors = ex.getFieldErrors();
        List<String> messages = allErrors.stream().map(error -> INVALID_FIELD + error.getField()).collect(Collectors.toList());
        BindExceptionModel model = new BindExceptionModel(messages, BAD_REQUEST);
        return super.handleExceptionInternal(ex, model, headers, status, request);
    }

    @ExceptionHandler(value = EntityNotFoundException.class)
    protected ResponseEntity<Object> handleConflict(EntityNotFoundException ex, WebRequest request) {
        LOGGER.error(ex.getMessage(), ex);
        ExceptionModel body = new ExceptionModel(ex.getMessage(), ENTITY_NOT_FOUND_EXCEPTION);
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = EntityAlreadyExistsException.class)
    protected ResponseEntity<Object> handleConflict(EntityAlreadyExistsException ex, WebRequest request) {
        LOGGER.error(ex.getMessage(), ex);
        ExceptionModel body = new ExceptionModel(ex.getMessage(), ENTITY_EXISTS_EXCEPTION);
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

//    @ExceptionHandler(value = MethodArgumentNotValidException.class)
//    protected ResponseEntity<Object> handleConflict(MethodArgumentNotValidException ex, WebRequest request) {
//        LOGGER.error(ex.getMessage(), ex);
//        ExceptionModel body = new ExceptionModel(ex.getMessage(), ENTITY_EXISTS_EXCEPTION);
//        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.CONFLICT, request);
//    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        LOGGER.error(ex.getMessage(), ex);
        String first = ex
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .findFirst()
                .orElse("validation failed");
        ExceptionModel body = new ExceptionModel(first, BAD_REQUEST);
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = ValidationException.class)
    protected ResponseEntity<Object> handleConflict(ValidationException ex, WebRequest request) {
        LOGGER.error(ex.getMessage(), ex);
        ExceptionModel body = new ExceptionModel(ex.getMessage(), BAD_REQUEST);
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @Override
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        LOGGER.error(ex.getMessage(), ex);
        ExceptionModel body = new ExceptionModel(ex.getMessage(), NOT_FOUND_EXCEPTION);
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = Exception.class)
    protected ResponseEntity<Object> handleConflict(Exception ex, WebRequest request) {
        LOGGER.error(ex.getMessage(), ex);
        ExceptionModel body = new ExceptionModel(INTERNAL_SERVER_ERROR_MESSAGE, INTERNAL_SERVER_ERROR);
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

}
//todo 29.04.2021 validation, patch(read about & implement), test verify,
// + new unit tests, handle required exceptions
//todo 29.04.2021 put much data to db, data audit + java docks
