package org.example.user.dao;

import org.example.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class UserDaoJdbcTest {

    private IUserDao userDaoJdbc;
    private User user1;
    private User user2;
    private User user3;

    @BeforeEach
    void setUp(){
        ApplicationContext applicationContext = new GenericXmlApplicationContext("applicationContext.xml");
        userDaoJdbc = applicationContext.getBean("userDaoJdbc", UserDaoJdbc.class);
        user1 = new User("test1","1234","테스터1");
        user2 = new User("test2","12345","테스터2");
        user3 = new User("test3","123456","테스터3");
    }
    @Test
    public void addAndGet(){

        userDaoJdbc.deleteAll();
        assertThat(userDaoJdbc.getCount()).isEqualTo(0);

        userDaoJdbc.add(user1);
        userDaoJdbc.add(user2);
        assertThat(userDaoJdbc.getCount()).isEqualTo(2);

        User findUser1 = userDaoJdbc.get("test1");
        assertThat(findUser1.getName()).isEqualTo(user1.getName());
        assertThat(findUser1.getPassword()).isEqualTo(user1.getPassword());

        User findUser2 = userDaoJdbc.get("test2");
        assertThat(findUser2.getName()).isEqualTo(user2.getName());
        assertThat(findUser2.getPassword()).isEqualTo(user2.getPassword());
    }

    @Test
    public void count(){

        userDaoJdbc.deleteAll();
        assertThat(userDaoJdbc.getCount()).isEqualTo(0);

        userDaoJdbc.add(user1);
        userDaoJdbc.add(user2);
        userDaoJdbc.add(user3);

        assertThat(userDaoJdbc.getCount()).isEqualTo(3);
    }

    @Test
    public void getUserWithInvalidId() {

        userDaoJdbc.deleteAll();
        assertThat(userDaoJdbc.getCount()).isEqualTo(0);

        assertThatThrownBy(() -> userDaoJdbc.get("test1")).isInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    public void getAll() {
        userDaoJdbc.deleteAll();

        userDaoJdbc.add(user1);
        List<User> findUsers = userDaoJdbc.getAll();
        assertThat(findUsers).hasSize(1);

        userDaoJdbc.add(user2);
        findUsers = userDaoJdbc.getAll();
        assertThat(findUsers).hasSize(2);

        userDaoJdbc.add(user3);
        findUsers = userDaoJdbc.getAll();
        assertThat(findUsers).hasSize(3);

    }

    @Test
    public void duplicateId(){
        userDaoJdbc.deleteAll();
        userDaoJdbc.add(user1);
        assertThatThrownBy(()-> userDaoJdbc.add(user1)).isInstanceOf(DataAccessException.class);
    }
}