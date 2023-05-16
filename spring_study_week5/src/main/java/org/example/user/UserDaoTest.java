package org.example.user;

import org.example.user.dao.strategy.UserDao;
import org.example.user.domain.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.sql.SQLException;

public class UserDaoTest {

    public static void main(String[] args) throws SQLException {
        ApplicationContext applicationContext = new GenericXmlApplicationContext("applicationContext.xml");
        //ApplicationContext applicationContext = new AnnotationConfigApplicationContext(DaoFactory.class);
        UserDao userDao = applicationContext.getBean("userDao", UserDao.class);

        User user = new User();
        user.setId("sksim");
        user.setPassword("1234");
        user.setName("심승경");
        userDao.add(user);

        User findUser = userDao.get("sksim");
        if(!findUser.getName().equals(user.getName())){
            System.out.print("테스트 실패 (name)");
        }else if(!findUser.getPassword().equals(user.getPassword())){
            System.out.println("테스트 실패 (password)");
        }else{
            System.out.println("조회 테스트 성공");
        }

    }
}
