package com.example.demo.error;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class EmployeeExceptionAdvice {

    @ExceptionHandler(value = {RuntimeException.class})
    public ResponseEntity<CustomErrorResponse> handleRuntimeExceptions(RuntimeException e){
        log.error(String.valueOf(e.getCause()));
        log.error("Runtime Error :", e);
        CustomErrorResponse customErrorResponse = new CustomErrorResponse("ERROR", e.getMessage());
        return new ResponseEntity<>(customErrorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<CustomErrorResponse> handleMethodArgumentNotValidExceptions(MethodArgumentNotValidException e){
        log.error("Request Param Validation Error :", e);
        StringBuilder errors = new StringBuilder();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            errors.append(fieldName + StringUtils.SPACE + "is missing;");
            log.error(error.getDefaultMessage());
        });

        CustomErrorResponse customErrorResponse = new CustomErrorResponse("ERROR", e.getMessage());
        return new ResponseEntity<>(customErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ResponseEntity<CustomErrorResponse> handleConstraintViolationExceptions(ConstraintViolationException e){
        log.error("Request Param Validation Error :", e);

        CustomErrorResponse customErrorResponse = new CustomErrorResponse("ERROR", e.getMessage());
        return new ResponseEntity<>(customErrorResponse, HttpStatus.BAD_REQUEST);
    }


}
