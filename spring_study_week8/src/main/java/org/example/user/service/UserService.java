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

        for(User user : users){
            Boolean changed = false;

            if(user.getLevel() == Level.BASIC && user.getLogin() >= 50){
                user.setLevel(Level.SILVER);
                changed = true;
            }else if(user.getLevel() == Level.SILVER && user.getRecommend() >= 30){
                user.setLevel(Level.GOLD);
                changed = true;
            }

            if(changed){
                userDao.update(user);
            }
        }
    }
}
