package com.kakao.todolist.service;

import com.kakao.todolist.common.ToDoException;
import com.kakao.todolist.entity.ParentMapping;
import com.kakao.todolist.entity.ToDo;
import com.kakao.todolist.entity.ToDoWithParents;
import com.kakao.todolist.repository.ParentMappingRepository;
import com.kakao.todolist.repository.ToDoRepository;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ToDoServiceTest {

    @Mock
    ToDoRepository toDoRepository;

    @Mock
    ParentMappingRepository parentMappingRepository;

    @InjectMocks
    ToDoService subject;

    @Test
    public void whenGetToDos_thenFindAllToDos() {
        when(toDoRepository.findAll()).thenReturn(getToDos());

        List<ToDo> toDos = subject.getToDos();

        verify(toDoRepository).findAll();

        assertEquals(1, toDos.get(0).getId());
        assertEquals(2, toDos.get(1).getId());
    }

    @Test
    public void whenGetToDosWithPaging_thenFindAllWIthPaging() {
        ArrayList<ToDo> toDos = getToDos();

        PageRequest pageRequest = PageRequest.of(0, 10, Sort.Direction.ASC, "id");

        Page<ToDo> pageToDo = new PageImpl<>(toDos);
        when(toDoRepository.findAll(pageRequest)).thenReturn(pageToDo);

        Page<ToDo> toDosWithPaging = subject.getToDosWithPaging(pageRequest);

        assertEquals(1, toDosWithPaging.getTotalPages());
        assertEquals(2, toDosWithPaging.getTotalElements());
    }



    @Test
    public void whenCallProcessingToDos_thenReturnProcessingToDos() {
        ArrayList<ToDo> toDos = getToDos();

        ToDo notProgressingToDo = new ToDo();
        notProgressingToDo.setId(3);
        notProgressingToDo.setIsProgress(false);
        toDos.add(notProgressingToDo);

        when(toDoRepository.findAll()).thenReturn(toDos);

        subject.progressingToDos();

        ArgumentCaptor captor = ArgumentCaptor.forClass(List.class);
        verify(toDoRepository).findAllById(((List<Integer>) captor.capture()));

        List<Integer> progressingIds = (List<Integer>) captor.getValue();
        assertEquals(2, progressingIds.size());
        assertEquals(1, ((int) progressingIds.get(0)));
        assertEquals(2, ((int) progressingIds.get(1)));
    }

    @Test
    public void whenToDoIsPresent_thenReturnToDo() throws ToDoException {
        ToDo toDo = new ToDo();
        toDo.setId(1);

        when(toDoRepository.findById(1)).thenReturn(Optional.of(toDo));

        ToDo returnToDo = subject.getToDo(1);

        assertEquals(returnToDo.getId(), 1);
    }

    @Test(expected = ToDoException.class)
    public void whenGetToDo_ifToDoIsNotPresent_thenThrowException() throws ToDoException {
        when(toDoRepository.findById(1)).thenReturn(Optional.empty());

        subject.getToDo(1);
    }

    @Test
    public void whenSaveToDo_thenSave() throws ToDoException {
        ToDo savedToDo = new ToDo();
        savedToDo.setId(1);

        ToDo inputToDo = new ToDo();

        when(toDoRepository.save(inputToDo)).thenReturn(savedToDo);

        ToDoWithParents toDoWithParents = new ToDoWithParents();
        toDoWithParents.setToDo(inputToDo);
        toDoWithParents.setParents(Lists.newArrayList());

        ToDo toDo = subject.createToDo(toDoWithParents);

        verify(toDoRepository).save(inputToDo);
        assertEquals(1, toDo.getId());
    }

    @Test
    public void whenCreateToDoWithParents_thenCreate() throws ToDoException {
        ToDo savedToDo = new ToDo();
        savedToDo.setId(1);
        savedToDo.setIsProgress(true);
        savedToDo.setWhatToDo("test");
        savedToDo.setWhatToDoWithLink("test");

        ToDo inputToDo = new ToDo();

        when(toDoRepository.save(inputToDo)).thenReturn(savedToDo);

        ToDoWithParents toDoWithParents = new ToDoWithParents();
        toDoWithParents.setToDo(inputToDo);
        toDoWithParents.setParents(Lists.newArrayList(2, 3));

        ToDo parent1 = new ToDo();
        parent1.setId(2);
        ToDo parent2 = new ToDo();
        parent1.setId(3);
        when(toDoRepository.findById(2)).thenReturn(Optional.of(parent1));
        when(toDoRepository.findById(3)).thenReturn(Optional.of(parent2));

        subject.createToDo(toDoWithParents);

        ArgumentCaptor argumentCaptor = ArgumentCaptor.forClass(ToDo.class);
        verify(toDoRepository, times(2)).save(((ToDo) argumentCaptor.capture()));
        List<ToDo> allValues = argumentCaptor.getAllValues();
        assertEquals("test @2 @3", allValues.get(1).getWhatToDoWithLink());
    }

    @Test
    public void whenLinkedToDoIsAllDone_thenReturnTrue() {
        ParentMapping parentMapping = new ParentMapping();
        parentMapping.setParentId(1);
        parentMapping.setToDoId(2);
        parentMapping.setIsProgress(false);

        ParentMapping parentMapping2 = new ParentMapping();
        parentMapping2.setParentId(1);
        parentMapping2.setToDoId(3);
        parentMapping2.setIsProgress(false);

        List<ParentMapping> linkedMappings = Lists.newArrayList(parentMapping, parentMapping2);

        when(parentMappingRepository.findByParentId(1)).thenReturn(linkedMappings);

        boolean linkedAllDone = subject.isLinkedAllDone(1);

        assertTrue(linkedAllDone);
    }

    @Test
    public void whenLinkedToDoIsNotAllDone_thenReturnFalse() {
        ParentMapping parentMapping = new ParentMapping();
        parentMapping.setParentId(1);
        parentMapping.setToDoId(2);
        parentMapping.setIsProgress(true);

        ParentMapping parentMapping2 = new ParentMapping();
        parentMapping2.setParentId(1);
        parentMapping2.setToDoId(3);
        parentMapping2.setIsProgress(false);

        List<ParentMapping> linkedMappings = Lists.newArrayList(parentMapping, parentMapping2);

        when(parentMappingRepository.findByParentId(1)).thenReturn(linkedMappings);

        boolean linkedAllDone = subject.isLinkedAllDone(1);

        assertFalse(linkedAllDone);
    }

    @Test
    public void whenDoneToDo_thenSaveIsProgressFalse() throws ToDoException {
        ToDo toDo = new ToDo();
        toDo.setId(1);
        toDo.setIsProgress(true);
        when(toDoRepository.findById(1)).thenReturn(Optional.of(toDo));

        ParentMapping parentMapping = new ParentMapping(1, 2, true);
        ParentMapping parentMapping2 = new ParentMapping(1, 3, true);

        when(parentMappingRepository.findByToDoId(1)).thenReturn(Lists.newArrayList(parentMapping, parentMapping2));

        subject.doneToDo(1);

        ArgumentCaptor captor = ArgumentCaptor.forClass(ToDo.class);
        verify(toDoRepository).save(((ToDo) captor.capture()));

        assertFalse(((ToDo) captor.getValue()).getIsProgress());

        ArgumentCaptor parentCaptor = ArgumentCaptor.forClass(List.class);
        verify(parentMappingRepository).saveAll((List<ParentMapping>) parentCaptor.capture());

        List<ParentMapping> parentMappings = (List<ParentMapping>) parentCaptor.getValue();
        assertFalse(parentMappings.get(0).getIsProgress());
        assertFalse(parentMappings.get(1).getIsProgress());
    }

    @Test
    public void whenGetLinkedToDoCall_thenGetLinkedToDoIdsRelatedToDoId() throws ToDoException {
        ParentMapping parentMapping = new ParentMapping();
        parentMapping.setParentId(1);
        parentMapping.setToDoId(2);
        parentMapping.setIsProgress(true);

        ParentMapping parentMapping2 = new ParentMapping();
        parentMapping2.setParentId(1);
        parentMapping2.setToDoId(3);
        parentMapping2.setIsProgress(false);

        List<ParentMapping> linkedMappings = Lists.newArrayList(parentMapping, parentMapping2);

        when(parentMappingRepository.findByParentId(1)).thenReturn(linkedMappings);

        ToDo toDo = new ToDo();
        toDo.setId(2);
        toDo.setWhatToDo("job");

        ToDo toDo2 = new ToDo();
        toDo2.setId(3);
        toDo2.setWhatToDo("job2");

        when(toDoRepository.findById(2)).thenReturn(Optional.of(toDo));
        when(toDoRepository.findById(3)).thenReturn(Optional.of(toDo2));

        List<ToDo> linkedToDo = subject.getLinkedToDo(1);

        assertEquals(2, linkedToDo.size());
        assertEquals(2, linkedToDo.get(0).getId());
        assertEquals("job", linkedToDo.get(0).getWhatToDo());
        assertEquals(3, linkedToDo.get(1).getId());
        assertEquals("job2", linkedToDo.get(1).getWhatToDo());
    }

    @Test
    public void whenDeleteToDo_thenDeleteToDoAndDeleteMappings() throws ToDoException {
        ParentMapping parentMapping1 = new ParentMapping(1, 2, true);
        ParentMapping parentMapping2 = new ParentMapping(1, 2, false);

        ToDo toDo = new ToDo();
        toDo.setId(1);

        when(toDoRepository.findById(1)).thenReturn(Optional.of(toDo));

        subject.deleteToDo(1);

        verify(toDoRepository).delete(toDo);
    }

    @Test(expected = ToDoException.class)
    public void whenDeleteToDo_ifToDoIdIsNotExist_thenThrowToDoException() throws ToDoException {
        when(toDoRepository.findById(1)).thenReturn(Optional.empty());

        subject.deleteToDo(1);
    }

    private ArrayList<ToDo> getToDos() {
        ToDo toDo1 = new ToDo();
        toDo1.setId(1);
        toDo1.setIsProgress(true);

        ToDo toDo2 = new ToDo();
        toDo2.setId(2);
        toDo2.setIsProgress(true);

        return Lists.newArrayList(toDo1, toDo2);
    }
}