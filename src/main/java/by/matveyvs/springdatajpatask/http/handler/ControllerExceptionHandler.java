package by.matveyvs.springdatajpatask.http.handler;

import by.matveyvs.springdatajpatask.exception.IllegalAgeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(value = IllegalAgeException.class)
    public String handleAgeException(Exception exception){
        log.info(exception.getMessage());
        return "/error/age";
    }
}
