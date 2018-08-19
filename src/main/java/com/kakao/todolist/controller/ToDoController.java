package com.kakao.todolist.controller;

import com.kakao.todolist.common.ToDoException;
import com.kakao.todolist.entity.ToDo;
import com.kakao.todolist.entity.ToDoWithParents;
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
    public ResponseEntity<ToDo> createToDo(@RequestBody ToDoWithParents toDoWithParents) throws ToDoException {
        ToDo returnToDo = toDoService.saveToDo(toDoWithParents);
        return new ResponseEntity<>(returnToDo, HttpStatus.CREATED);
    }

    @PostMapping("/{toDoId}/{whatToDo}")
    public ResponseEntity<Void> updateWhatToDo(@PathVariable Integer toDoId, @PathVariable String whatToDo) throws ToDoException {
        toDoService.updateWhatToDo(toDoId, whatToDo);
        return new ResponseEntity<>(HttpStatus.RESET_CONTENT);
    }

    @DeleteMapping("/{toDoId}")
    public ResponseEntity<Void> deleteToDo(@PathVariable Integer toDoId) throws ToDoException {
        toDoService.deleteToDo(toDoId);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/{toDoId}")
    public ResponseEntity<ToDo> getToDo(@PathVariable Integer toDoId) throws ToDoException {
        ToDo toDo = toDoService.getToDo(toDoId);
        return new ResponseEntity<>(toDo, HttpStatus.OK);
    }

    @GetMapping("/{toDoId}/checkalldone")
    public ResponseEntity<Boolean> checkAllDone(@PathVariable Integer toDoId) {
        return new ResponseEntity<>(toDoService.isLinkedAllDone(toDoId), HttpStatus.OK);
    }

    @PostMapping("/{toDoId}/done")
    public ResponseEntity<Void> doneToDo(@PathVariable Integer toDoId) throws ToDoException {
        toDoService.doneToDo(toDoId);
        return new ResponseEntity<>(HttpStatus.RESET_CONTENT);
    }

}
