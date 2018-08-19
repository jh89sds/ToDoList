package com.kakao.todolist.entity;

import java.io.Serializable;

public class ParentMappingIds implements Serializable {
    private int toDoId;

    private int parentId;

    public ParentMappingIds() {
    }

    public ParentMappingIds(int toDoId, int parentId) {
        this.toDoId = toDoId;
        this.parentId = parentId;
    }

    public int getToDoId() {
        return toDoId;
    }

    public void setToDoId(int toDoId) {
        this.toDoId = toDoId;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }
}
