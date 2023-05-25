package org.example;

import org.example.user.dao.UserDao;
import org.example.user.domain.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args)  {
        ApplicationContext applicationContext = new GenericXmlApplicationContext("applicationContext.xml");
        //ApplicationContext applicationContext = new AnnotationConfigApplicationContext(DaoFactory.class);
        UserDao userDao = applicationContext.getBean("userDao", UserDao.class);

        User user = new User();
        user.setId("sksim3");
        user.setPassword("1234");
        user.setName("심승경");
        userDao.add(user);

    }
}