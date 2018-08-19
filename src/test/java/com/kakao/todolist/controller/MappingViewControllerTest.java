package com.kakao.todolist.controller;

import com.kakao.todolist.common.ToDoException;
import com.kakao.todolist.entity.PageInfo;
import com.kakao.todolist.entity.ToDo;
import com.kakao.todolist.service.ToDoService;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.ui.Model;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles("local")
public class MappingViewControllerTest {

    @Mock
    ToDoService toDoService;

    @InjectMocks
    private MappingViewController subject;

    @Test
    public void whenGetToDosCall_thenReturnListViewWithPageable() {

        Model model = mock(Model.class);

        PageRequest request = PageRequest.of(0, 10, Sort.Direction.ASC, "id");

        Page<ToDo> toDoWithPaging = new PageImpl<>(makeToDoList());
        when(toDoService.getToDosWithPaging(request)).thenReturn(toDoWithPaging);

        String view = subject.getToDos(model, 1);

        ArgumentCaptor captorKey = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor captorList = ArgumentCaptor.forClass(ToDo.class);
        verify(model, times(4)).addAttribute((String) captorKey.capture(), captorList.capture());

        List allKeys = captorKey.getAllValues();
        List allValues = captorList.getAllValues();

        assertEquals("toDos", allKeys.get(0));
        assertEquals("pages", allKeys.get(1));
        assertEquals("totalPage", allKeys.get(2));
        assertEquals("hostUrl", allKeys.get(3));

        assertEquals(2, ((Page<ToDo>) allValues.get(0)).getTotalElements());
        assertEquals(1, ((Page<ToDo>) allValues.get(0)).getTotalPages());
        assertEquals(1, ((List<PageInfo>) allValues.get(1)).get(0).getPage());
        assertEquals(1, allValues.get(2));

        assertEquals("list", view);
    }

    @Test
    public void whenUpdateToDoViewCall_thenReturnUpdateView() throws ToDoException {
        Model model = mock(Model.class);

        ToDo toDo = new ToDo();
        toDo.setId(3);

        when(toDoService.getToDo(3)).thenReturn(toDo);
        List<ToDo> linkedToDos = makeToDoList();
        when(toDoService.getLinkedToDo(3)).thenReturn(linkedToDos);
        when(toDoService.isLinkedAllDone(3)).thenReturn(true);

        String view = subject.updateToDoView(model, 3);

        ArgumentCaptor captorKey = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor captorValue = ArgumentCaptor.forClass(Object.class);

        verify(model, times(4)).addAttribute(((String) captorKey.capture()), captorValue.capture());

        List allKeys = captorKey.getAllValues();
        List allValues = captorValue.getAllValues();

        assertEquals("toDo", allKeys.get(0));
        assertEquals("linkedToDo", allKeys.get(1));
        assertEquals("isLinkedAllDone", allKeys.get(2));
        assertEquals("hostUrl", allKeys.get(3));

        assertEquals(toDo, allValues.get(0));
        assertEquals(linkedToDos, allValues.get(1));
        assertEquals(true, allValues.get(2));

        assertEquals("update", view);
    }

    @Test
    public void whenCreateToDoViewCall_thenReturnCreateView() {
        
        Model model = mock(Model.class);

        List<ToDo> toDos = makeToDoList();

        when(toDoService.progressingToDos()).thenReturn(toDos);

        String view = subject.createToDoView(model);

        ArgumentCaptor captorKey = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor captorList = ArgumentCaptor.forClass(ToDo.class);
        verify(model, times(2)).addAttribute((String) captorKey.capture(), captorList.capture());

        List allKeys = captorKey.getAllValues();
        List allValues = captorList.getAllValues();

        assertEquals("toDos", allKeys.get(0));
        assertEquals("hostUrl", allKeys.get(1));

        List<ToDo> modelToDos = (List<ToDo>) allValues.get(0);
        assertEquals(1, modelToDos.get(0).getId());
        assertEquals(2, modelToDos.get(1).getId());

        assertEquals("create", view);
    }

    private List<ToDo> makeToDoList() {
        ToDo toDo = new ToDo();
        toDo.setId(1);

        ToDo toDo2 = new ToDo();
        toDo2.setId(2);

        return Lists.newArrayList(toDo, toDo2);
    }
}