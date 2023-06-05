package org.example.user.attribute;

import org.example.user.dao.IUserDao;
import org.example.user.domain.User;
import org.example.user.enums.Level;


public class DefaultUserLevelUpgradePolicy implements UserLevelUpgradePolicy{

    private IUserDao userDao;

    private static final int MIN_LOGIN_COUNT_FOR_SILVER = 50;

    private static final int MIN_RECOMMEND_FOR_GOLD = 30;
    public void setUserDao(IUserDao userDao){
        this.userDao = userDao;
    }

    public boolean canUpgradeLevel(User user){
        Level currentLevel = user.getLevel();

        switch(currentLevel){
            case BASIC: return (user.getLogin() >= MIN_LOGIN_COUNT_FOR_SILVER);
            case SILVER: return (user.getRecommend() >= MIN_RECOMMEND_FOR_GOLD);
            case GOLD:
            case PLATINUM:
                return false;
            default: throw new IllegalArgumentException("Unknown Level :"+currentLevel);
        }
    }

    public void upgradeLevel(User user) {
        user.upgradeLevel();
        userDao.update(user);
    }
}
