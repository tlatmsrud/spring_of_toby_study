package org.example.user.dao.templateMethod;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {

        UserDaoDeleteAll userDaoDeleteAll = new UserDaoDeleteAll();
        userDaoDeleteAll.deleteAll();

    }
}
