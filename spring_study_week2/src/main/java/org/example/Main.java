package org.example;

import org.example.user.dao.UserDao;
import org.example.user.domain.User;
import org.example.user.factory.ConnectionMaker;
import org.example.user.factory.CountingConnectionMaker;
import org.example.user.factory.DConnectionMaker;
import org.example.user.factory.DaoFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        ApplicationContext applicationContext = new GenericXmlApplicationContext("applicationContext.xml");
        UserDao userDao = applicationContext.getBean("userDao", UserDao.class);
        User user = new User();
        user.setId("test123");
        user.setName("테스터");
        user.setPassword("1234");
        userDao.add(user);
    }
}