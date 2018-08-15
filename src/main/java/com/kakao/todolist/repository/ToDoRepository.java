package com.kakao.todolist.repository;

import com.kakao.todolist.entity.ToDo;
import org.springframework.data.repository.CrudRepository;

public interface ToDoRepository extends CrudRepository<ToDo, Integer> {
}
