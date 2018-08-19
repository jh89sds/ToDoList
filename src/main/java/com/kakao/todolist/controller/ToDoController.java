package com.kakao.todolist.controller;

import com.kakao.todolist.common.ToDoException;
import com.kakao.todolist.entity.ToDo;
import com.kakao.todolist.entity.ToDoWithParents;
import com.kakao.todolist.service.ToDoService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation(value = "할일 목록 조회")
    @GetMapping
    public ResponseEntity<List<ToDo>> getToDos() {
        List<ToDo> toDoList = toDoService.getToDos();
        return new ResponseEntity<>(toDoList, HttpStatus.OK);
    }

    @ApiOperation(value = "할일 생성")
    @PostMapping
    public ResponseEntity<ToDo> createToDo(@RequestBody ToDoWithParents toDoWithParents) throws ToDoException {
        ToDo returnToDo = toDoService.createToDo(toDoWithParents);
        return new ResponseEntity<>(returnToDo, HttpStatus.CREATED);
    }

    @ApiOperation(value = "할일명 변경")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "toDoId", value = "변경할 할일 ID", required = true, dataType = "int", paramType = "path", defaultValue = "1"),
            @ApiImplicitParam(name = "whatToDo", value = "변경할 할일 ID", required = true, dataType = "string", paramType = "path", defaultValue = "새로운 할 일"),
    })
    @PutMapping("/{toDoId}/{whatToDo}")
    public ResponseEntity<Void> updateWhatToDo(@PathVariable Integer toDoId, @PathVariable String whatToDo) throws ToDoException {
        toDoService.updateWhatToDo(toDoId, whatToDo);
        return new ResponseEntity<>(HttpStatus.RESET_CONTENT);
    }

    @ApiOperation(value = "할일 삭제")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "toDoId", value = "삭제할 할일 ID", required = true, dataType = "int", paramType = "path", defaultValue = "3"),
    })
    @DeleteMapping("/{toDoId}")
    public ResponseEntity<Void> deleteToDo(@PathVariable Integer toDoId) throws ToDoException {
        toDoService.deleteToDo(toDoId);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @ApiOperation(value = "할일 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "toDoId", value = "조회할 할일 ID", required = true, dataType = "int", paramType = "path", defaultValue = "1"),
    })
    @GetMapping("/{toDoId}")
    public ResponseEntity<ToDo> getToDo(@PathVariable Integer toDoId) throws ToDoException {
        ToDo toDo = toDoService.getToDo(toDoId);
        return new ResponseEntity<>(toDo, HttpStatus.OK);
    }

    @ApiOperation(value = "참조된 할일들이 다 되었는지 여부를 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "toDoId", value = "조회할 할일 ID", required = true, dataType = "int", paramType = "path", defaultValue = "1"),
    })
    @GetMapping("/{toDoId}/checkalldone")
    public ResponseEntity<Boolean> checkAllDone(@PathVariable Integer toDoId) {
        return new ResponseEntity<>(toDoService.isLinkedAllDone(toDoId), HttpStatus.OK);
    }

    @ApiOperation(value = "할일 완료")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "toDoId", value = "완료할 할일 ID", required = true, dataType = "int", paramType = "path", defaultValue = "3"),
    })
    @PutMapping("/{toDoId}/done")
    public ResponseEntity<Void> doneToDo(@PathVariable Integer toDoId) throws ToDoException {
        toDoService.doneToDo(toDoId);
        return new ResponseEntity<>(HttpStatus.RESET_CONTENT);
    }

}
