package org.example.user.service;

import org.example.user.attribute.DefaultUserLevelUpgradePolicy;
import org.example.user.attribute.UserLevelUpgradePolicy;
import org.example.user.dao.IUserDao;
import org.example.user.dao.UserDaoJdbc;
import org.example.user.domain.User;
import org.example.user.enums.Level;
import org.example.user.proxy.TransactionHandler;
import org.junit.jupiter.api.BeforeEach;
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
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.mock;

/**
 * title        :
 * author       : sim
 * date         : 2023-07-05
 * description  :
 */

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "/test-applicationContext.xml")
public class DynamicProxyUserServiceTest {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @SpyBean
    private IUserDao userDao;

    @SpyBean
    private DefaultUserLevelUpgradePolicy userLevelUpgradePolicy;

    private final List<User> users = Arrays.asList(
            new User("test1","테스터1","pw1", Level.BASIC, 49, 0, "tlatmsrud@naver.com"),
            new User("test2","테스터2","pw2", Level.BASIC, 50, 0, "tlatmsrud@naver.com"),
            new User("test3","테스터3","pw3", Level.SILVER, 60, 29, "tlatmsrud@naver.com"),
            new User("test4","테스터4","pw4", Level.SILVER, 60, 30, "tlatmsrud@naver.com"),
            new User("test5","테스터5","pw5", Level.GOLD, 100, 100, "tlatmsrud@naver.com")
    );
    @BeforeEach
    void setUp(){
        userService.setUserDao(userDao);
        userService.setUserLevelUpgradePolicy(userLevelUpgradePolicy);

        given(userDao.getAll()).willReturn(users);

        willThrow(new RuntimeException()).given(userLevelUpgradePolicy).upgradeLevel(users.get(3));
    }

    @Test
    void upgradeAllOrNothingWithNoProxy(){

        // 테이블 데이터 초기화
        userDao.deleteAll();

        // 테이블에 유저 정보 insert
        users.forEach(user -> userDao.add(user));

        // 유저 레벨 업그레이드 메서드 실행 및 예외 발생 여부 확인 (setUp 메서드에 3번째 유저 업그레이드 처리 시 예외 발생하도록 스터빙 추가)
        assertThatThrownBy(() -> userService.upgradeLevels())
                .isInstanceOf(RuntimeException.class);

        // DB
        assertThat(userDao.get("test1").getLevel()).isEqualTo(Level.BASIC);
        assertThat(userDao.get("test2").getLevel()).isEqualTo(Level.SILVER); // 트랜잭션이 적용되지 않아 BASIC 레벨로 롤백되지 않음.
        assertThat(userDao.get("test3").getLevel()).isEqualTo(Level.SILVER);
        assertThat(userDao.get("test4").getLevel()).isEqualTo(Level.SILVER);
        assertThat(userDao.get("test5").getLevel()).isEqualTo(Level.GOLD);

    }

    @Test
    void upgradeAllOrNothingWithProxy(){

        // 부가기능 핸들러 객체 생성
        TransactionHandler txHandler = new TransactionHandler();
        txHandler.setTarget(userService);
        txHandler.setTransactionManager(transactionManager);
        txHandler.setPattern("upgradeLevels");

        // 다이나믹 프록시 생성
        UserService proxyUserService = (UserService) Proxy.newProxyInstance(
                getClass().getClassLoader() // 다이나믹 프록시 클래스의 로딩에 사용할 클래스 로더
                ,new Class[] {UserService.class} // 구현할 인터페이스
                ,txHandler // 부가 기능과 위임 코드를 담은 InvocationHandler
        );

        // 테이블 데이터 초기화
        userDao.deleteAll();

        // 테이블에 유저 정보 insert
        users.forEach(user -> userDao.add(user));

        assertThatThrownBy(() -> proxyUserService.upgradeLevels())
                .isInstanceOf(RuntimeException.class);

        assertThat(userDao.get("test1").getLevel()).isEqualTo(Level.BASIC);
        assertThat(userDao.get("test2").getLevel()).isEqualTo(Level.BASIC); // 트랜잭션이 적용되지 않아 BASIC 레벨로 롤백됨.
        assertThat(userDao.get("test3").getLevel()).isEqualTo(Level.SILVER);
        assertThat(userDao.get("test4").getLevel()).isEqualTo(Level.SILVER);
        assertThat(userDao.get("test5").getLevel()).isEqualTo(Level.GOLD);
    }
}
