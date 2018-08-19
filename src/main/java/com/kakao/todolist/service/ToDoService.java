package com.kakao.todolist.service;

import com.kakao.todolist.common.ExceptionCase;
import com.kakao.todolist.common.ToDoException;
import com.kakao.todolist.entity.ParentMapping;
import com.kakao.todolist.entity.ToDo;
import com.kakao.todolist.entity.ToDoWithParents;
import com.kakao.todolist.repository.ParentMappingRepository;
import com.kakao.todolist.repository.ToDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ToDoService {
    @Autowired
    ToDoRepository toDoRepository;

    @Autowired
    ParentMappingRepository parentMappingRepository;

    public List<ToDo> getToDos() {
        return (List<ToDo>) toDoRepository.findAll();
    }

    public Page<ToDo> getToDosWithPaging(PageRequest pageRequest) {
        return toDoRepository.findAll(pageRequest);
    }

    public List<ToDo> progressingToDos() {
        List<ToDo> toDos = getToDos();
        List<Integer> progressingToDos = toDos.stream()
                .filter(ToDo::getIsProgress)
                .map(ToDo::getId)
                .collect(Collectors.toList());
        return (List<ToDo>) toDoRepository.findAllById(progressingToDos);
    }

    public ToDo getToDo(int toDoId) throws ToDoException {
        try {
            return toDoRepository.findById(toDoId).get();
        }catch (NoSuchElementException e) {
            throw new ToDoException("TODO ID : " + toDoId, ExceptionCase.NOT_EXIST);
        }
    }

    public ToDo saveToDo(ToDoWithParents toDoWithParents) throws ToDoException {
        ToDo toDo = toDoWithParents.getToDo();
        List<Integer> parents = toDoWithParents.getParents();
        try {
            toDo.setWhatToDoWithLink(toDo.getWhatToDo());
            toDo.setRegisterDate(LocalDateTime.now());
            ToDo savedToDo = toDoRepository.save(toDo);

            return customizeToDoColumnWithLinkSave(parents, savedToDo);

        } catch (DataAccessException e) {
            throw new ToDoException("TODO ID : " + toDo.getId(), ExceptionCase.SAVE_FAIL);
        }
    }

    public void deleteToDo(int toDoId) throws ToDoException {
        try {
            deleteParentMappings(toDoId);
            ToDo toDo = getToDo(toDoId);
            toDoRepository.delete(toDo);
        } catch (DataAccessException e) {
            throw new ToDoException("TODO ID : " + toDoId, ExceptionCase.DELETE_FAIL);
        }
    }

    public boolean isLinkedAllDone(int toDoId) {
        List<ParentMapping> linkedMappings = parentMappingRepository.findByParentId(toDoId);
        List<ParentMapping> progressList = linkedMappings.stream()
                .filter(linkedMapping -> linkedMapping.getIsProgress())
                .collect(Collectors.toList());

        return progressList.size() == 0;
    }

    public List<ToDo> getLinkedToDo(int toDoId) throws ToDoException {
        List<ToDo> linkedToDos = new ArrayList<>();

        List<Integer> linkedIds = getLinkedIds(toDoId);

        for (Integer linkedId : linkedIds) {
            linkedToDos.add(getToDo(linkedId));
        }
        return linkedToDos;
    }

    public void doneToDo(Integer toDoId) throws ToDoException {
        if(isLinkedAllDone(toDoId)) {
            ToDo toDo = getToDo(toDoId);

            toDo.setIsProgress(false);
            toDo.setLastUpdateDate(LocalDateTime.now());

            toDoRepository.save(toDo);

            List<ParentMapping> parentMappings = parentMappingRepository.findByToDoId(toDoId);
            parentMappings.forEach(parentMapping -> parentMapping.setIsProgress(false));

            parentMappingRepository.saveAll(parentMappings);
        } else {
            throw new ToDoException("TODO_ID : " + toDoId, ExceptionCase.LINKED_NOT_DONE);
        }
    }

    public void updateWhatToDo(int toDoId, String whatToDo) throws ToDoException {
        ToDo toDo = getToDo(toDoId);

        String beforeWhatToDo = toDo.getWhatToDo();

        toDo.setWhatToDo(whatToDo);
        String whatToDoWithLink = toDo.getWhatToDoWithLink();
        String changedLink = whatToDoWithLink.replace(beforeWhatToDo, whatToDo);

        toDo.setWhatToDoWithLink(changedLink);
        toDo.setLastUpdateDate(LocalDateTime.now());

        try {
            toDoRepository.save(toDo);
        } catch (DataAccessException e) {
            throw new ToDoException("TODO_ID : " + toDoId, ExceptionCase.SAVE_FAIL);
        }
    }

    private ToDo customizeToDoColumnWithLinkSave(List<Integer> parents, ToDo savedToDo) throws ToDoException {
        if(parents.size() > 0) {
            for (Integer parent : parents) {
                //parent가 없는 경우 예외처리 위한 조회
                getToDo(parent);
                parentMapping(savedToDo, parent);
                savedToDo.setWhatToDoWithLink(savedToDo.getWhatToDoWithLink().concat(" @").concat(String.valueOf(parent)));
            }
            return toDoRepository.save(savedToDo);
        } else {
            return savedToDo;
        }
    }

    private void parentMapping(ToDo toDo, Integer parent) throws ToDoException {
        try {
            ParentMapping parentMapping = new ParentMapping(toDo.getId(), parent, toDo.getIsProgress());
            parentMappingRepository.save(parentMapping);
        } catch (NullPointerException e) {
            throw new ToDoException("TODO IS NOT EXIST. PARENT_ID : " + parent, ExceptionCase.NOT_EXIST);
        } catch (DataAccessException e) {
            throw new ToDoException("TODO_ID : " + toDo.getId(), ExceptionCase.SAVE_FAIL);
        }
    }

    private List<Integer> getLinkedIds(int toDoId) {
        List<ParentMapping> linkedMappings = parentMappingRepository.findByParentId(toDoId);

        return linkedMappings.stream()
                .map(ParentMapping::getToDoId)
                .collect(Collectors.toList());
    }

    private void deleteParentMappings(int toDoId) throws ToDoException {
        try {
            List<ParentMapping> parentMappings = parentMappingRepository.findByParentId(toDoId);
            for (ParentMapping parentMapping : parentMappings) {
                if(parentMapping.getIsProgress()) {
                    throw new ToDoException("TODO_ID : " + toDoId, ExceptionCase.LINKED_NOT_DONE);
                }
            }

            parentMappingRepository.deleteAll(parentMappings);
        }catch (DataAccessException e) {
            throw new ToDoException("PARENT_MAPPING TO_DO ID : " + toDoId, ExceptionCase.DELETE_FAIL);
        }

    }
}
