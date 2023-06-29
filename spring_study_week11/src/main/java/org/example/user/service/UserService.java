package org.example.user.service;

import org.example.user.attribute.UserLevelUpgradePolicy;
import org.example.user.dao.IUserDao;
import org.example.user.domain.User;
import org.example.user.enums.Level;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import java.util.List;

/**
 * title        :
 * author       : sim
 * date         : 2023-06-02
 * description  :
 */
public class UserService {

    private IUserDao userDao;


    private PlatformTransactionManager transactionManager;

    private UserLevelUpgradePolicy userLevelUpgradePolicy;
    public void setUserDao(IUserDao userDao){
        this.userDao = userDao;
    }

    public void setTransactionManager(PlatformTransactionManager transactionManager){
        this.transactionManager = transactionManager;
    }

    public void setUserLevelUpgradePolicy(UserLevelUpgradePolicy userLevelUpgradePolicy){
        this.userLevelUpgradePolicy = userLevelUpgradePolicy;
    }

    public void upgradeLevels() {

        TransactionStatus status = transactionManager.getTransaction(
                new DefaultTransactionDefinition()
        );

        try{
            upgradeLevelsInternal();
            transactionManager.commit(status);
        }catch(Exception e){
            transactionManager.rollback(status);
        }
    }

    private void upgradeLevelsInternal(){
        List<User> users = userDao.getAll(); // DB /
        for(User user : users) {
            if (userLevelUpgradePolicy.canUpgradeLevel(user)) {
                userLevelUpgradePolicy.upgradeLevel(user);
            }
        }
    }
    public void add(User user) {
        if(user.getLevel() == null){
            user.setLevel(Level.BASIC);
        }
        userDao.add(user);
    }
}
