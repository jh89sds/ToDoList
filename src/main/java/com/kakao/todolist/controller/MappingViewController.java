package com.kakao.todolist.controller;

import com.kakao.todolist.common.ToDoException;
import com.kakao.todolist.entity.PageInfo;
import com.kakao.todolist.entity.ToDo;
import com.kakao.todolist.service.ToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/view")
public class MappingViewController {
    @Autowired
    ToDoService toDoService;

    @Value("${todolist.host.url}")
    private String hostUrl;

    @GetMapping("/{pageNo}")
    public String getToDos(Model model, @PathVariable int pageNo) {
        PageRequest request = PageRequest.of(pageNo - 1, 10, Sort.Direction.ASC, "id");
        Page<ToDo> toDosWithPaging = toDoService.getToDosWithPaging(request);
        model.addAttribute("toDos", toDosWithPaging);

        List<PageInfo> pages = new ArrayList<>();

        for(int i = 1; i <= toDosWithPaging.getTotalPages(); i++) {
            PageInfo pageInfo = new PageInfo();
            pageInfo.setPage(i);
            if(pageNo == i) {
                pageInfo.setIsActive("active");
            }
            pages.add(pageInfo);
        }
        model.addAttribute("pages", pages);
        model.addAttribute("totalPage", pages.size());
        model.addAttribute("hostUrl", hostUrl);
        return "list";
    }

    @GetMapping("/todo/{toDoId}")
    public String updateToDoView(Model model, @PathVariable int toDoId) throws ToDoException {
        model.addAttribute("toDo", toDoService.getToDo(toDoId));
        model.addAttribute("linkedToDo", toDoService.getLinkedToDo(toDoId));
        model.addAttribute("isLinkedAllDone", toDoService.isLinkedAllDone(toDoId));
        model.addAttribute("hostUrl", hostUrl);
        return "update";
    }

    @GetMapping("/todo/create")
    public String createToDoView(Model model) {
        model.addAttribute("toDos", toDoService.progressingToDos());
        model.addAttribute("hostUrl", hostUrl);
        return "create";
    }
}
