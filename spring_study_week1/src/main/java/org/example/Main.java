package org.example;

import org.example.user.dao.UserDao;
import org.example.user.domain.User;
import org.example.user.factory.ConnectionMaker;
import org.example.user.factory.DConnectionMaker;
import org.example.user.factory.DaoFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(DaoFactory.class);
        UserDao userDao = applicationContext.getBean("userDao", UserDao.class);
        User user = new User();
        user.setId("test");
        user.setName("심승경");
        user.setPassword("1234");

        userDao.add(user);

        System.out.println("등록 성공");

        User findUser = userDao.get(user.getId());
        System.out.println(findUser.getName());
        System.out.println("조회 성공");
    }
}