package org.example.user.service;


import org.example.user.attribute.UserLevelUpgradePolicy;
import org.example.user.dao.IUserDao;
import org.example.user.domain.User;
import org.example.user.enums.Level;
import org.example.user.proxy.TransactionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.PlatformTransactionManager;

import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "/test-applicationContext.xml")
class UserServiceTest {

    @Autowired
    private PlatformTransactionManager transactionManager;

    @SpyBean
    private IUserDao userDao;

    @Autowired
    private UserServiceImpl userServiceImpl;

    private final UserLevelUpgradePolicy userLevelUpgradePolicy = mock(UserLevelUpgradePolicy.class);
    private final List<User> users = Arrays.asList(
            new User("test1","테스터1","pw1", Level.BASIC, 49, 0, "tlatmsrud@naver.com"),
            new User("test2","테스터2","pw2", Level.BASIC, 50, 0, "tlatmsrud@naver.com"),
            new User("test3","테스터3","pw3", Level.SILVER, 60, 29, "tlatmsrud@naver.com"),
            new User("test4","테스터4","pw4", Level.SILVER, 60, 30, "tlatmsrud@naver.com"),
            new User("test5","테스터5","pw5", Level.GOLD, 100, 100, "tlatmsrud@naver.com")
   );

    @BeforeEach
    void setUp(){
        userServiceImpl.setUserDao(userDao);
        userServiceImpl.setUserLevelUpgradePolicy(userLevelUpgradePolicy);

        given(userDao.getAll()).willReturn(users);
        willDoNothing().given(userDao).add(any(User.class));

        given(userLevelUpgradePolicy.upgradeLevel(any(User.class))).will(invocation -> {
           User source = invocation.getArgument(0);
           return source.getId();
        });
    }

    @Test
    @DisplayName("업그레이드 레벨 테스트")
    void upgradeLevels(){

        userServiceImpl.upgradeLevels();

        verify(userDao).getAll();
        verify(userLevelUpgradePolicy,times(5)).canUpgradeLevel(any(User.class));
        verify(userLevelUpgradePolicy).upgradeLevel(users.get(1));
        verify(userLevelUpgradePolicy).upgradeLevel(users.get(3));
    }

    @Test
    void upgradleAllOrNoting() throws Exception{

        TransactionHandler txHandler = new TransactionHandler();
        txHandler.setTarget(userServiceImpl);
        txHandler.setTransactionManager(transactionManager);
        txHandler.setPattern("upgradeLevels");

        UserService txUserService = (UserService) Proxy.newProxyInstance(
                getClass().getClassLoader()
                ,new Class[] {UserService.class}
                ,txHandler);
    }

    @Test
    @DisplayName("레벨이 할당되지 않은 User 등록")
    void addWithNotAssignLevel(){
        User user = users.get(0);
        user.setLevel(null);

        userServiceImpl.add(user);
        assertThat(user.getLevel()).isEqualTo(Level.BASIC);

        verify(userDao).add(any(User.class));
    }

    @Test
    @DisplayName("레벨이 할당된 User 등록")
    void addWithAssignLevel(){

        User user = users.get(0);
        Level userLevel = user.getLevel();

        userServiceImpl.add(user);

        assertThat(user.getLevel()).isEqualTo(userLevel);
    }
}