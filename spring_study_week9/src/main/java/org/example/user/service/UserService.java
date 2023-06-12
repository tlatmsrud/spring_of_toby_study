package org.example.user.service;

import org.example.user.attribute.UserLevelUpgradePolicy;
import org.example.user.dao.IUserDao;
import org.example.user.domain.User;
import org.example.user.enums.Level;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * title        :
 * author       : sim
 * date         : 2023-06-02
 * description  :
 */
public class UserService {

    private IUserDao userDao;

    private DataSource dataSource;

    private UserLevelUpgradePolicy userLevelUpgradePolicy;
    public void setUserDao(IUserDao userDao){
        this.userDao = userDao;
    }

    public void setDataSource(DataSource dataSource){
        this.dataSource = dataSource;
    }

    public void setUserLevelUpgradePolicy(UserLevelUpgradePolicy userLevelUpgradePolicy){
        this.userLevelUpgradePolicy = userLevelUpgradePolicy;
    }

    public void upgradeLevels() {
        TransactionSynchronizationManager.initSynchronization(); // 동기화 작업
        Connection c = DataSourceUtils.getConnection(dataSource); // 커넥션 생성 및 저장소 바인딩

        try{
            c.setAutoCommit(false);

            List<User> users = userDao.getAll();

            for(User user : users) {

                if (userLevelUpgradePolicy.canUpgradeLevel(user)) {
                    userLevelUpgradePolicy.upgradeLevel(user);
                }
            }
            c.commit();
        }catch(Exception e){
            try{
                c.rollback();
            }catch (SQLException ex){
                e.printStackTrace();
            }
        }finally {
            DataSourceUtils.releaseConnection(c, dataSource); // DB Connection close
            TransactionSynchronizationManager.unbindResource(this.dataSource); // 동기화 작업 종료
            TransactionSynchronizationManager.clearSynchronization(); // 동기화 작업 종료
        }

    }

    public void add(User user) {
        if(user.getLevel() == null){
            user.setLevel(Level.BASIC);
        }
        userDao.add(user);
    }
}
