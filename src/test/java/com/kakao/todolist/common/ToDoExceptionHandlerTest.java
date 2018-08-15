package com.kakao.todolist.common;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ToDoExceptionHandlerTest {

    private ToDoExceptionHandler subject;

    @Before
    public void setUp() {
        subject = new ToDoExceptionHandler();
        subject.logger = mock(Logger.class);
    }

    @Test
    public void whenDataNotExist_thenShowNotExistErrorMessage() {
        ToDoException toDoException = new ToDoException("TODO ID : " + 1, ExceptionCase.NOT_EXIST);

        ResponseEntity<ExceptionCase> response = subject.handleException(toDoException);

        verify(subject.logger).error("TODO ID : " + 1);
        verify(subject.logger).error("Data is not exist. Please check the ID");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(ExceptionCase.NOT_EXIST, response.getBody());
    }

    @Test
    public void whenSaveFailed_thenShowSaveFailMessage() {
        ToDoException toDoException = new ToDoException("TODO ID : " + 1, ExceptionCase.SAVE_FAIL);

        ResponseEntity<ExceptionCase> response = subject.handleException(toDoException);

        verify(subject.logger).error("TODO ID : " + 1);
        verify(subject.logger).error("Save Failed");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(ExceptionCase.SAVE_FAIL, response.getBody());
    }

    @Test
    public void whenDeleteFailed_thenShowDeleteFailMessage() {
        ToDoException toDoException = new ToDoException("TODO ID : " + 1, ExceptionCase.DELETE_FAIL);

        ResponseEntity<ExceptionCase> response = subject.handleException(toDoException);

        verify(subject.logger).error("TODO ID : " + 1);
        verify(subject.logger).error("Delete Failed");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(ExceptionCase.DELETE_FAIL, response.getBody());
    }
}