package com.kakao.todolist.repository;

import com.kakao.todolist.entity.ToDo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ToDoRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private ToDoRepository toDoRepository;

    private final int TEST_ID = 1;

    @Test
    public void findToDoById() {
        ToDo returnToDo = testEntityManager.find(ToDo.class, TEST_ID);

        assertThat(returnToDo.getId(), is(1));
        assertThat(returnToDo.getWhatToDo(), is("가사일"));
        assertThat(returnToDo.getIsProgress(), is(true));
    }

    @Test
    public void saveToDo() {
        ToDo toDo = new ToDo();
        toDo.setWhatToDo("새로운 할 일");
        ToDo returnToDo = testEntityManager.persist(toDo);

        assertThat(returnToDo.getId(), not(0));
        assertThat(returnToDo.getWhatToDo(), is("새로운 할 일"));
    }
}