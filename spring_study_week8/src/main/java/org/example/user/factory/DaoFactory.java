package org.example.user.factory;

import org.example.user.attribute.DefaultUserLevelUpgradePolicy;
import org.example.user.attribute.UserLevelUpgradePolicy;
import org.example.user.dao.UserDaoJdbc;
import org.example.user.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;

@Configuration
public class DaoFactory {
    @Bean
    public UserDaoJdbc userDao(){
        UserDaoJdbc userDaoJdbc = new UserDaoJdbc();
        userDaoJdbc.setDataSource(dataSource());
        return userDaoJdbc;
    }

    @Bean
    public DataSource dataSource(){
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();

        dataSource.setDriverClass(com.mysql.jdbc.Driver.class);
        dataSource.setUrl("jdbc:mysql://localhost/spring");
        dataSource.setUsername("tlatmsrud");
        dataSource.setPassword("tla1203#");

        return dataSource;
    }

    @Bean
    public UserService userService(){
        UserService userService = new UserService();
        userService.setUserDao(userDao());
        userService.setUserLevelUpgradePolicy(userLevelUpgradePolicy());
        return userService;
    }

    @Bean
    public UserLevelUpgradePolicy userLevelUpgradePolicy(){
        DefaultUserLevelUpgradePolicy policy = new DefaultUserLevelUpgradePolicy();
        policy.setUserDao(userDao());
        return policy;
    }
}
