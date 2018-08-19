package com.kakao.todolist.repository;

import com.kakao.todolist.entity.ParentMapping;
import com.kakao.todolist.entity.ParentMappingIds;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ParentMappingRepositoryTest {
    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private ParentMappingRepository parentMappingRepository;

    @Test
    public void findParentMappingByToDoId() {
        ParentMappingIds parentMappingIds = new ParentMappingIds();
        parentMappingIds.setToDoId(2);
        parentMappingIds.setParentId(1);

        ParentMapping parentMapping = testEntityManager.find(ParentMapping.class, parentMappingIds);

        assertThat(parentMapping.getToDoId(), is(2));
        assertThat(parentMapping.getParentId(), is(1));
        assertThat(parentMapping.getIsProgress(), is(true));
    }
}