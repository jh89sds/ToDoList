package com.kakao.todolist.repository;

import com.kakao.todolist.entity.ToDo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

public interface ToDoRepository extends CrudRepository<ToDo, Integer> {
    Iterable<ToDo> findAll(Sort sort);

    Page<ToDo> findAll(Pageable pageable);
}
