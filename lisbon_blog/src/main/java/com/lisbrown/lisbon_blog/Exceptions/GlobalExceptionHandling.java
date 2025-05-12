package com.lisbrown.lisbon_blog.Exceptions;

import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandling  {

    @ExceptionHandler(ConfigDataResourceNotFoundException.class)
    public ProblemDetail handleResourceNotFoundException(ResponseEntity<ResourcesNotFoundException> exception){
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setTitle("Resource Not Found Exception");
        problemDetail.setDetail(String.valueOf(exception.getBody()));
        problemDetail.setProperty("Timestamp", LocalTime.now());
      return problemDetail;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleInternalServerError(Exception exception){
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        problemDetail.setTitle(String.valueOf(exception.getCause()));
        problemDetail.setDetail(String.valueOf(exception.getMessage()));
        problemDetail.setProperty("Timestamp", LocalTime.now());
        return problemDetail;
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleMethodArgumentNotValidException(MethodArgumentNotValidException exception)
    {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Bad Request Error");
        Map<String, List<String>> fieldErrors = exception.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.groupingBy(
                        FieldError::getField,
                        Collectors.mapping(FieldError::getDefaultMessage, Collectors.toList())
                ));
        problemDetail.setTitle("Request failed to bad parameter/s");
        problemDetail.setProperty("FieldError", fieldErrors);
        problemDetail.setProperty("timestamp", LocalDateTime.now());
        return problemDetail;
    }
}
