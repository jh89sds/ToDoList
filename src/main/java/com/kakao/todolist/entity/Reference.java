package com.kakao.todolist.entity;

import javax.persistence.Embeddable;

@Embeddable
public class Reference {
    private int childId;
    private String childStatus;

    public int getChildId() {
        return childId;
    }

    public void setChildId(int childId) {
        this.childId = childId;
    }

    public String getChildStatus() {
        return childStatus;
    }

    public void setChildStatus(String childStatus) {
        this.childStatus = childStatus;
    }
}
