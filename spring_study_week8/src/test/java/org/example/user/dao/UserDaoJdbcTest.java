package org.example.user.dao;

import org.example.user.domain.User;
import org.example.user.enums.Level;
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

    private IUserDao userDao;
    private User user1;
    private User user2;
    private User user3;

    @BeforeEach
    void setUp(){
        ApplicationContext applicationContext = new GenericXmlApplicationContext("applicationContext.xml");
        userDao = applicationContext.getBean("userDaoJdbc", UserDaoJdbc.class);
        user1 = new User("test1","1234","테스터1", Level.BASIC, 1, 0);
        user2 = new User("test2","12345","테스터2", Level.SILVER, 55, 10);
        user3 = new User("test3","123456","테스터3", Level.GOLD, 100, 40);
    }
    @Test
    public void addAndGet(){

        userDao.deleteAll();
        assertThat(userDao.getCount()).isEqualTo(0);

        userDao.add(user1);
        userDao.add(user2);
        assertThat(userDao.getCount()).isEqualTo(2);

        User findUser1 = userDao.get("test1");
        checkSameUser(findUser1, user1);

        User findUser2 = userDao.get("test2");
        checkSameUser(findUser2, user2);
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

    @Test
    public void duplicateId(){
        userDao.deleteAll();
        userDao.add(user1);
        assertThatThrownBy(()-> userDao.add(user1)).isInstanceOf(DataAccessException.class);
    }

    @Test
    public void update(){
        userDao.deleteAll();

        userDao.add(user1);
        userDao.add(user2);

        user1.setName("수정자");
        user1.setPassword("updatePw");
        user1.setLevel(Level.GOLD);
        user1.setLogin(1000);
        user1.setRecommend(999);

        int updateCnt = userDao.update(user1);


        User updateUser = userDao.get(user1.getId());
        checkSameUser(updateUser,user1);

        User noUpdateUser = userDao.get(user2.getId());
        checkSameUser(user2,noUpdateUser);

        assertThat(updateCnt).isEqualTo(1);
    }
    public void checkSameUser(User user1, User user2){
        assertThat(user1.getName()).isEqualTo(user2.getName());
        assertThat(user1.getPassword()).isEqualTo(user2.getPassword());
        assertThat(user1.getLevel()).isEqualTo(user2.getLevel());
        assertThat(user1.getLogin()).isEqualTo(user2.getLogin());
        assertThat(user1.getRecommend()).isEqualTo(user2.getRecommend());
    }
}