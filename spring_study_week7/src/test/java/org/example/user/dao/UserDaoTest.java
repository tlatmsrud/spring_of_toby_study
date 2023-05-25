package org.example.user.dao;

import org.assertj.core.api.ThrowableAssert;
import org.example.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;

import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class UserDaoTest {

    private UserDao userDao;
    private User user1;
    private User user2;
    private User user3;

    @BeforeEach
    void setUp(){
        ApplicationContext applicationContext = new GenericXmlApplicationContext("applicationContext.xml");
        userDao = applicationContext.getBean("userDao", UserDao.class);
        user1 = new User("test1","1234","테스터1");
        user2 = new User("test2","12345","테스터2");
        user3 = new User("test3","123456","테스터3");
    }
    @Test
    public void addAndGet(){

        userDao.deleteAll();
        assertThat(userDao.getCount()).isEqualTo(0);

        userDao.add(user1);
        userDao.add(user2);
        assertThat(userDao.getCount()).isEqualTo(2);

        User findUser1 = userDao.get("test1");
        assertThat(findUser1.getName()).isEqualTo(user1.getName());
        assertThat(findUser1.getPassword()).isEqualTo(user1.getPassword());

        User findUser2 = userDao.get("test2");
        assertThat(findUser2.getName()).isEqualTo(user2.getName());
        assertThat(findUser2.getPassword()).isEqualTo(user2.getPassword());
    }

    @Test
    public void count(){

        userDao.deleteAll();
        assertThat(userDao.getCount()).isEqualTo(0);

        userDao.add(user1);
        userDao.add(user2);
        userDao.add(user3);

        assertThat(userDao.getCount()).isEqualTo(3);
    }

    @Test
    public void getUserWithInvalidId() {

        userDao.deleteAll();
        assertThat(userDao.getCount()).isEqualTo(0);

        assertThatThrownBy(() -> userDao.get("test1")).isInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    public void getAll() {
        userDao.deleteAll();

        userDao.add(user1);
        List<User> findUsers = userDao.getAll();
        assertThat(findUsers).hasSize(1);

        userDao.add(user2);
        findUsers = userDao.getAll();
        assertThat(findUsers).hasSize(2);

        userDao.add(user3);
        findUsers = userDao.getAll();
        assertThat(findUsers).hasSize(3);

    }
}