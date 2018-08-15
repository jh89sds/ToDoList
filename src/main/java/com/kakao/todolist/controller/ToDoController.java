package com.kakao.todolist.controller;

import com.kakao.todolist.common.ToDoException;
import com.kakao.todolist.entity.ToDo;
import com.kakao.todolist.service.ToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
public class ToDoController {

    @Autowired
    ToDoService toDoService;

    @GetMapping
    public ResponseEntity<List<ToDo>> getToDos() {
        List<ToDo> toDoList = toDoService.getToDos();
        return new ResponseEntity<>(toDoList, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<ToDo> saveToDo(@RequestBody ToDo toDo) throws ToDoException {
        ToDo returnToDo = toDoService.saveToDo(toDo);
        return new ResponseEntity<>(returnToDo, HttpStatus.CREATED);
    }

    @DeleteMapping("/{toDoId}")
    public ResponseEntity<Void> deleteToDo(@PathVariable String toDoId) throws ToDoException {
        toDoService.deleteToDo(toDoId);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/{toDoId}")
    public ResponseEntity<ToDo> getToDo(@PathVariable String toDoId) throws ToDoException {
        ToDo toDo = toDoService.getToDo(toDoId);
        return new ResponseEntity<>(toDo, HttpStatus.OK);
    }
}
