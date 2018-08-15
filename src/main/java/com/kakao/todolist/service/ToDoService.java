package com.kakao.todolist.service;

import com.kakao.todolist.common.ExceptionCase;
import com.kakao.todolist.common.ToDoException;
import com.kakao.todolist.entity.ToDo;
import com.kakao.todolist.repository.ToDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ToDoService {
    @Autowired
    ToDoRepository toDoRepository;

    public List<ToDo> getToDos() {
        return (List<ToDo>) toDoRepository.findAll();
    }

    public ToDo getToDo(String toDoId) throws ToDoException {
        try {
            return toDoRepository.findById(Integer.parseInt(toDoId)).get();
        }catch (NoSuchElementException e) {
            throw new ToDoException("TODO ID : " + toDoId, ExceptionCase.NOT_EXIST);
        }
    }

    public ToDo saveToDo(ToDo toDo) throws ToDoException {
        try {
            return toDoRepository.save(toDo);
        } catch (DataAccessException e) {
            throw new ToDoException("TODO ID : " + toDo.getId(), ExceptionCase.SAVE_FAIL);
        }

    }

    public void deleteToDo(String toDoId) throws ToDoException {
        try {
            ToDo toDo = toDoRepository.findById(Integer.parseInt(toDoId)).get();
            toDoRepository.delete(toDo);
        } catch (NoSuchElementException e) {
            throw new ToDoException("TODO ID : " + toDoId, ExceptionCase.NOT_EXIST);
        } catch (DataAccessException e) {
            throw new ToDoException("TODO ID : " + toDoId, ExceptionCase.DELETE_FAIL);
        }
    }
}
