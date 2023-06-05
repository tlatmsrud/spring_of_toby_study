package org.example.user.service;

import org.example.user.dao.IUserDao;
import org.example.user.domain.User;
import org.example.user.enums.Level;

import java.util.List;

/**
 * title        :
 * author       : sim
 * date         : 2023-06-02
 * description  :
 */
public class UserService {

    private IUserDao userDao;

    public void setUserDao(IUserDao userDao){
        this.userDao = userDao;
    }

    public void upgradeLevels() {
        List<User> users = userDao.getAll();

        for(User user : users) {

            if (canUpgradeLevel(user)) {
                upgradeLevel(user);
            }
        }
    }



    public void add(User user) {
        if(user.getLevel() == null){
            user.setLevel(Level.BASIC);
        }
        userDao.add(user);
    }

    private boolean canUpgradeLevel(User user){
        Level currentLevel = user.getLevel();
        switch(currentLevel){
            case BASIC: return (user.getLogin() >= 50);
            case SILVER: return (user.getRecommend() >= 30);
            case GOLD: return false;
            default: throw new IllegalArgumentException("Unknown Level :"+currentLevel);
        }
    }

    private void upgradeLevel(User user) {
        user.upgradeLevel();
        userDao.update(user);
    }
}
