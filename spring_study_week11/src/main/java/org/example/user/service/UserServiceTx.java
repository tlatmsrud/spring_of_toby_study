package org.example.user.service;

import org.example.user.domain.User;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * title        :
 * author       : sim
 * date         : 2023-06-29
 * description  :
 */
public class UserServiceTx implements UserService{

    private PlatformTransactionManager transactionManager;
    private UserService userService;

    public void setTransactionManager(PlatformTransactionManager transactionManager){
        this.transactionManager = transactionManager;
    }

    public void setUserService(UserService userService){
        this.userService = userService;
    }

    @Override
    public void upgradeLevels() {
        TransactionStatus status = transactionManager.getTransaction(
                new DefaultTransactionDefinition()
        );

        try{
            userService.upgradeLevels();
            transactionManager.commit(status);
        }catch(Exception e){
            transactionManager.rollback(status);
        }
    }

    @Override
    public void add(User user) {
        TransactionStatus status = transactionManager.getTransaction(
                new DefaultTransactionDefinition()
        );
            userService.add(user);
        try{

            transactionManager.commit(status);
        }catch(Exception e){
            transactionManager.rollback(status);
        }
    }
}
