package com.kakao.todolist.controller;

import com.kakao.todolist.service.ToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view")
public class MappingViewController {
    @Autowired
    ToDoService toDoService;

    @GetMapping
    public String getMain(Model model) {
        model.addAttribute("toDos", toDoService.getToDos());
        return "todos";
    }
}
