package com.kakao.todolist.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ToDoExceptionHandler {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(ToDoException.class)
    protected ResponseEntity<ExceptionCase> handleException(ToDoException todoException) {
        logger.error(todoException.getMessage());

        switch (todoException.getExceptionCase()) {
            case NOT_EXIST:
                logger.error("Data is not exist. Please check the ID");
                break;
            case SAVE_FAIL:
                logger.error("Save Failed");
                break;
            case DELETE_FAIL:
                logger.error("Delete Failed");
        }
        return new ResponseEntity<>(todoException.getExceptionCase(), HttpStatus.BAD_REQUEST);
    }
}