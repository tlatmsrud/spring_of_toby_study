package org.example;

import org.example.user.dao.UserDaoJdbc;
import org.example.user.domain.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class Main {
    public static void main(String[] args)  {
        ApplicationContext applicationContext = new GenericXmlApplicationContext("applicationContext.xml");
        //ApplicationContext applicationContext = new AnnotationConfigApplicationContext(DaoFactory.class);
        UserDaoJdbc userDaoJdbc = applicationContext.getBean("userDao", UserDaoJdbc.class);

        User user = new User();
        user.setId("sksim3");
        user.setPassword("1234");
        user.setName("심승경");
        userDaoJdbc.add(user);

    }
}