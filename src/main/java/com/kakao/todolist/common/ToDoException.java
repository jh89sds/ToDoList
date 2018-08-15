package com.kakao.todolist.common;

public class ToDoException extends Exception {

    private ExceptionCase exceptionCase;

    public ToDoException(String message, ExceptionCase exceptionCase) {
        super(message);
        this.exceptionCase = exceptionCase;
    }

    public ExceptionCase getExceptionCase() {
        return exceptionCase;
    }
}
