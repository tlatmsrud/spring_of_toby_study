package org.example.user.dao;

import org.example.user.factory.ConnectionMaker;
import org.example.user.domain.User;
import org.example.user.factory.DConnectionMaker;

import java.sql.*;

public class UserDao {

    private ConnectionMaker connectionMaker;

    public UserDao(ConnectionMaker connectionMaker){
        this.connectionMaker = connectionMaker;
    }

    public void add(User user) throws ClassNotFoundException, SQLException {

        Connection c = connectionMaker.makeConnection();

        // 실행시킬 쿼리 생성
        PreparedStatement ps = c.prepareStatement(
                "insert into users(id, name, password) values(?,?,?)");

        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());

        // execute : 수행 결과를 Boolean 타입으로 반환
        // executeQuery : select 구문을 처리할 때 사용하며, 수행 결과를 ResultSet 타입으로 반환
        // executeUpdate : INSERT, UPDATE, DELETE 구문을 처리할 때 사용하며, 반영된 레코드 수를 int 타입으로 반환.
        ps.executeUpdate();

        // Close
        ps.close();
        c.close();
    }

    public User get(String id) throws ClassNotFoundException, SQLException{

        Connection c = connectionMaker.makeConnection();

        // 쿼리 및 파라미터 셋팅
        PreparedStatement ps = c.prepareStatement(
                "select * from users where id = ?");
        ps.setString(1, id);

        // executeQuery 메서드를 사용하여 실행 결과를 ResultSet으로 반환
        ResultSet rs = ps.executeQuery();

        // ResultSet 값이 존재하는지 확인하며 존재할 경우 해당 레코드(다음 레코드)로 이동
        rs.next();

        User user = new User();
        user.setId(rs.getString("id"));
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("password"));

        rs.close();
        ps.close();
        c.close();

        return user;
    }
}