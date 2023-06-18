package org.example.user.attribute;

import org.example.user.dao.IUserDao;
import org.example.user.domain.User;
import org.example.user.enums.Level;
import org.junit.jupiter.api.BeforeEach;
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
class DefaultUserLevelUpgradePolicyTest {

    @Autowired
    IUserDao userDao;

    @Autowired
    DefaultUserLevelUpgradePolicy userLevelUpgradePolicy;

    List<User> users; // 테스트 픽스처

    public static final int MIN_LOGIN_COUNT_FOR_SILVER = 50;

    public static final int MIN_RECCOMEND_FOR_GOLD = 30;
    @BeforeEach
    void setUp(){


        users = Arrays.asList(
                new User("test1","테스터1","pw1", Level.BASIC, MIN_LOGIN_COUNT_FOR_SILVER-1, 0, "tlatmsrud@naver.com"),
                new User("test2","테스터2","pw2", Level.BASIC, MIN_LOGIN_COUNT_FOR_SILVER, 0, "tlatmsrud@naver.com"),
                new User("test3","테스터3","pw3", Level.SILVER, 60, MIN_RECCOMEND_FOR_GOLD-1, "tlatmsrud@naver.com"),
                new User("test4","테스터4","pw4", Level.SILVER, 60, MIN_RECCOMEND_FOR_GOLD, "tlatmsrud@naver.com"),
                new User("test5","테스터5","pw5", Level.GOLD, 100, 100, "tlatmsrud@naver.com")
        );


    }
    @Test
    void canUpgradeLevel() {

        assertThat(userLevelUpgradePolicy.canUpgradeLevel(users.get(0))).isFalse();
        assertThat(userLevelUpgradePolicy.canUpgradeLevel(users.get(1))).isTrue();
        assertThat(userLevelUpgradePolicy.canUpgradeLevel(users.get(2))).isFalse();
        assertThat(userLevelUpgradePolicy.canUpgradeLevel(users.get(3))).isTrue();
        assertThat(userLevelUpgradePolicy.canUpgradeLevel(users.get(4))).isFalse();

    }

    @Test
    void upgradeLevel() {

        userLevelUpgradePolicy.upgradeLevel(users.get(1));
        assertThat(users.get(1).getLevel()).isEqualTo(Level.SILVER);

        userLevelUpgradePolicy.upgradeLevel(users.get(3));
        assertThat(users.get(3).getLevel()).isEqualTo(Level.GOLD);
    }
}