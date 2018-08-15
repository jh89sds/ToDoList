package com.kakao.todolist.service;

import com.kakao.todolist.common.ToDoException;
import com.kakao.todolist.entity.ToDo;
import com.kakao.todolist.repository.ToDoRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ToDoServiceTest {

    @Mock
    ToDoRepository toDoRepository;

    @InjectMocks
    ToDoService subject;

    @Test
    public void whenToDoIsPresent_thenReturnToDo() throws ToDoException {
        ToDo toDo = new ToDo();
        toDo.setId(1);

        when(toDoRepository.findById(1)).thenReturn(Optional.of(toDo));

        ToDo returnToDo = subject.getToDo("1");

        assertEquals(returnToDo.getId(), 1);
    }

    @Test(expected = ToDoException.class)
    public void whenGetToDo_ifToDoIsNotPresent_thenThrowException() throws ToDoException {
        when(toDoRepository.findById(1)).thenReturn(Optional.empty());

        subject.getToDo("1");
    }

    @Test
    public void whenSaveToDo_thenSave() throws ToDoException {
        ToDo savedToDo = new ToDo();
        savedToDo.setId(1);

        ToDo inputToDo = new ToDo();

        when(toDoRepository.save(inputToDo)).thenReturn(savedToDo);

        ToDo returnToDo = subject.saveToDo(inputToDo);

        assertEquals(1, returnToDo.getId());
    }

    @Test
    public void whenDeleteToDo_thenDelete() throws ToDoException {
        ToDo toDo = new ToDo();
        toDo.setId(1);

        when(toDoRepository.findById(1)).thenReturn(Optional.of(toDo));

        subject.deleteToDo("1");

        verify(toDoRepository).delete(toDo);
    }

    @Test(expected = ToDoException.class)
    public void whenDeleteToDo_ifToDoIdIsNotExist_thenThrowToDoException() throws ToDoException {
        when(toDoRepository.findById(1)).thenReturn(Optional.empty());

        subject.deleteToDo("1");
    }
}