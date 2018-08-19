package com.kakao.todolist.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(ParentMappingIds.class)
public class ParentMapping {
    public ParentMapping() {
    }

    public ParentMapping(int toDoId, int parentId, boolean isProgress) {
        this.toDoId = toDoId;
        this.parentId = parentId;
        this.isProgress = isProgress;
    }

    @Id
    private int toDoId;

    @Id
    private int parentId;

    private boolean isProgress;

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

    public boolean getIsProgress() {
        return isProgress;
    }

    public void setIsProgress(boolean isProgress) {
        this.isProgress = isProgress;
    }
}
