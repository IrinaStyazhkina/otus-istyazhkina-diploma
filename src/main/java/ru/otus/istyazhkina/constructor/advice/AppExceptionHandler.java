package ru.otus.istyazhkina.constructor.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler({AccessDeniedException.class, HttpRequestMethodNotSupportedException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public ResponseEntity<String> accessDeniedException(Exception ex) {
        return ResponseEntity.badRequest().body("У вас недостаточно прав для совершения данного дейсвтия!");
    }

    @ExceptionHandler()
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<String> exception(Exception ex) {
        String errorMessage = (ex != null ? ex.getMessage() : "Неизвестная ошибка!");
        return ResponseEntity.badRequest().body(errorMessage);
    }
}

