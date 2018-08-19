package com.kakao.todolist.controller;

import com.kakao.todolist.entity.ToDo;
import com.kakao.todolist.entity.ToDoWithParents;
import com.kakao.todolist.service.ToDoService;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ToDoController.class)
public class ToDoControllerTest extends ControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ToDoService toDoService;

    @Test
    public void whenGetToDos_thenCallGetGoDos() throws Exception {
        ToDo toDo = new ToDo();
        toDo.setId(1);
        ArrayList<ToDo> getToDos = Lists.newArrayList(toDo);

        when(toDoService.getToDos()).thenReturn(getToDos);

        MvcResult mvcResult = mvc.perform(get("/api/todos"))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(mvcResult.getResponse().getContentAsString(), containsString("\"id\":1"));

        verify(toDoService).getToDos();
    }

    @Test
    public void whenCreateToDo_thenCallSaveToDo() throws Exception {
        ToDo inputToDo = new ToDo();
        inputToDo.setWhatToDo("가사일");
        inputToDo.setIsProgress(true);

        ToDoWithParents toDoWithParents = new ToDoWithParents();
        toDoWithParents.setToDo(inputToDo);
        toDoWithParents.setParents(Lists.newArrayList());

        ToDo outputToDo = new ToDo();
        outputToDo.setId(1);

        when(toDoService.saveToDo(ArgumentMatchers.any(ToDoWithParents.class))).thenReturn(outputToDo);

        MvcResult mvcResult = mvc.perform(put("/api/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(toDoWithParents)))
                .andExpect(status().isCreated())
                .andReturn();

        assertThat(mvcResult.getResponse().getContentAsString(), containsString("\"id\":1"));

        ArgumentCaptor captor = ArgumentCaptor.forClass(ToDo.class);
        verify(toDoService).saveToDo(((ToDoWithParents) captor.capture()));

        ToDoWithParents argumentValue = (ToDoWithParents) captor.getValue();
        assertThat(argumentValue.getToDo().getWhatToDo(), is("가사일"));
        assertThat(argumentValue.getToDo().getIsProgress(), is(true));
    }

    @Test
    public void whenDeleteToDo_thenCallDeleteToDo() throws Exception {
        mvc.perform(delete("/api/todos/1"))
                .andExpect(status().isAccepted());

        verify(toDoService).deleteToDo(1);
    }

    @Test
    public void whenGetToDo_thenCallGetToDo() throws Exception {
        mvc.perform(get("/api/todos/1"))
                .andExpect(status().isOk());

        verify(toDoService).getToDo(1);
    }
}