package org.example.user.service;

import org.example.user.attribute.DefaultUserLevelUpgradePolicy;
import org.example.user.attribute.EventUserLevelUpgradePolicy;
import org.example.user.attribute.UserLevelUpgradePolicy;
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

    @Autowired
    DefaultUserLevelUpgradePolicy defaultUserLevelUpgradePolicy;

    @Autowired
    EventUserLevelUpgradePolicy eventUserLevelUpgradePolicy;

    List<User> users; // 테스트 픽스처

    @BeforeEach
    void setUp(){
       users = Arrays.asList(
               new User("test1","테스터1","pw1", Level.BASIC, 49, 0),
               new User("test2","테스터2","pw2", Level.BASIC, 50, 0),
               new User("test3","테스터3","pw3", Level.SILVER, 60, 29),
               new User("test4","테스터4","pw4", Level.SILVER, 60, 30),
               new User("test5","테스터5","pw5", Level.GOLD, 100, 100),
               new User("test6","테스터6","pw6", Level.PLATINUM, 100, 100)
       );
    }

    @Test
    @DisplayName("업그레이드 레벨 테스트-DefaultUserLevelUpgradePolicy")
    void upgradeLevelWithDefaultUserLevelUpgradePolicy(){
        userService.setUserLevelUpgradePolicy(defaultUserLevelUpgradePolicy);
        userDao.deleteAll();
        users.forEach(user -> userDao.add(user));

        userService.upgradeLevels();
        checkLevelUpgraded(users.get(0),false);
        checkLevelUpgraded(users.get(1),true);
        checkLevelUpgraded(users.get(2),false);
        checkLevelUpgraded(users.get(3),true);
        checkLevelUpgraded(users.get(4),false);
        checkLevelUpgraded(users.get(5),false);
    }

    @Test
    @DisplayName("업그레이드 레벨 테스트-EventUserLevelUpgradePolicy")
    void upgradeLevelWithEventUserLevelUpgradePolicy(){
        userService.setUserLevelUpgradePolicy(eventUserLevelUpgradePolicy);
        userDao.deleteAll();
        users.forEach(user -> userDao.add(user));

        userService.upgradeLevels();
        checkLevelUpgraded(users.get(0),true);
        checkLevelUpgraded(users.get(1),true);
        checkLevelUpgraded(users.get(2),true);
        checkLevelUpgraded(users.get(3),true);
        checkLevelUpgraded(users.get(4),true);
        checkLevelUpgraded(users.get(5),false);

    }

    @Test
    @DisplayName("레벨이 할당되지 않은 User 등록")
    void addWithNotAssignLevel(){
        userDao.deleteAll();

        User user = users.get(0);
        user.setLevel(null);

        userService.add(user);

        checkLevelUpgraded(userDao.get(user.getId()), false);
    }

    @Test
    @DisplayName("레벨이 할당된 User 등록")
    void addWithAssignLevel(){
        userDao.deleteAll();

        User user = users.get(4);
        userService.add(user);

        checkLevelUpgraded(userDao.get(user.getId()), false);
    }



    private void checkLevelUpgraded(User user, boolean upgraded){
        User userUpdate = userDao.get(user.getId());

        if(upgraded){
            assertThat(userUpdate.getLevel()).isEqualTo(user.getLevel().getNexeLevel());
        }else{
            assertThat(userUpdate.getLevel()).isEqualTo(user.getLevel());
        }
    }
}