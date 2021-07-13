package com.example.exception.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@Slf4j
@RestControllerAdvice(basePackages = "com.example.exception.controller")
//@ControllerAdvice // view기반
public class GlobalControllerAdvice {

    // Exception에 대한 예외처리
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity exception(Exception e) {

        System.out.println(e.getClass().getName());
        System.out.println("#####################");
        System.out.println(e.getLocalizedMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("");
    }

    // 특정 예외를 잡는 메서드
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity methodArgumentNotValidException(MethodArgumentNotValidException e) {

        BindingResult bindingResult = e.getBindingResult();

        bindingResult.getAllErrors().forEach(error->{
            FieldError fieldError = (FieldError) error;

            String fieldName = fieldError.getField();
            String message = fieldError.getDefaultMessage();
            String value = fieldError.getRejectedValue().toString();

            log.info("{}", fieldName);
            log.info("{}", message);
            log.info("{}", value);

        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity constraintViolationException(ConstraintViolationException e) {

        e.getConstraintViolations().forEach(error->{
            String fieldName = error.getPropertyPath().toString();
            String message = error.getMessage();
            String value = error.getInvalidValue().toString();

            log.info("{}", fieldName);
            log.info("{}", message);
            log.info("{}", value);
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public ResponseEntity missingServletRequestParameterException(MissingServletRequestParameterException e) {

        String fieldName = e.getParameterName();
        String message = e.getParameterType();
        String value = e.getMessage();

        log.info("{}", fieldName);
        log.info("{}", message);
        log.info("{}", value);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
