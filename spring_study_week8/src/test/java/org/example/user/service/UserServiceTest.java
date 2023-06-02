package org.example.user.service;

import org.example.user.dao.IUserDao;
import org.example.user.domain.User;
import org.example.user.enums.Level;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "/test-applicationContext.xml")
class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    IUserDao userDao;

    List<User> users; // 테스트 픽스처

    @BeforeEach
    void setUp(){
       users = Arrays.asList(
               new User("test1","테스터1","pw1", Level.BASIC, 49, 0),
               new User("test2","테스터2","pw2", Level.BASIC, 50, 0),
               new User("test3","테스터3","pw3", Level.SILVER, 60, 29),
               new User("test4","테스터4","pw4", Level.SILVER, 60, 30),
               new User("test5","테스터5","pw5", Level.GOLD, 100, 100)
       );
    }

    @Test
    @DisplayName("업그레이드 레벨 테스트")
    void upgradeLevel(){
        userDao.deleteAll();
        users.forEach(user -> userDao.add(user));

        userService.upgradeLevels();
        checkLevel(users.get(0),Level.BASIC);
        checkLevel(users.get(1),Level.SILVER);
        checkLevel(users.get(2),Level.SILVER);
        checkLevel(users.get(3),Level.GOLD);
        checkLevel(users.get(4),Level.GOLD);

    }

    private void checkLevel(User user, Level expectedLevel){
        User userUpdate = userDao.get(user.getId());
        assertThat(userUpdate.getLevel()).isEqualTo(expectedLevel);
    }
}