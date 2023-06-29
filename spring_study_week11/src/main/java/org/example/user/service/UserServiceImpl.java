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
public class UserServiceImpl implements UserService {

    private IUserDao userDao;

    private UserLevelUpgradePolicy userLevelUpgradePolicy;
    public void setUserDao(IUserDao userDao){
        this.userDao = userDao;
    }


    public void setUserLevelUpgradePolicy(UserLevelUpgradePolicy userLevelUpgradePolicy){
        this.userLevelUpgradePolicy = userLevelUpgradePolicy;
    }

    public void upgradeLevels() {
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
