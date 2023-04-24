package org.example;

import org.example.user.dao.UserDao;
import org.example.user.domain.User;
import org.example.user.factory.ConnectionMaker;
import org.example.user.factory.CountingConnectionMaker;
import org.example.user.factory.DConnectionMaker;
import org.example.user.factory.DaoFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(DaoFactory.class);
        UserDao userDao = applicationContext.getBean("userDao", UserDao.class);

        // DAO 사용 코드

        CountingConnectionMaker ccm = applicationContext.getBean("countingConnectionMaker",
                CountingConnectionMaker.class);

        System.out.println(ccm.getCounter());
    }
}