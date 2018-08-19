package com.kakao.todolist.repository;

import com.kakao.todolist.entity.ParentMapping;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ParentMappingRepository extends CrudRepository<ParentMapping, Integer> {
    List<ParentMapping> findByParentId(int toDoId);
    List<ParentMapping> findByToDoId(int toDoId);
}
