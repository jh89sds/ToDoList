package com.kakao.todolist.entity;

import java.util.List;

public class ToDoWithParents {
    private ToDo toDo;

    private List<Integer> parents;

    public ToDo getToDo() {
        return toDo;
    }

    public void setToDo(ToDo toDo) {
        this.toDo = toDo;
    }

    public List<Integer> getParents() {
        return parents;
    }

    public void setParents(List<Integer> parents) {
        this.parents = parents;
    }
}
