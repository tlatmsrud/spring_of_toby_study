package org.example.user.dao.strategy;

import org.example.user.domain.User;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource){
        this.dataSource = dataSource;
    }

    public void add(User user) throws SQLException {
        StatementStrategy strategy = new AddStatement(user);
        jdbcContextWithStatementStrategy(strategy);
    }

    public void deleteAll() throws SQLException{
        StatementStrategy strategy = new DeleteAllStatement();
        jdbcContextWithStatementStrategy(strategy);
    }

    public User get(String id) throws SQLException{
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            c = dataSource.getConnection();

            // 쿼리 및 파라미터 셋팅
            ps = c.prepareStatement(
                    "select * from users where id = ?");
            ps.setString(1, id);

            // executeQuery 메서드를 사용하여 실행 결과를 ResultSet으로 반환
            rs = ps.executeQuery();

            // ResultSet 값이 존재하는지 확인하며 존재할 경우 해당 레코드(다음 레코드)로 이동
            User user = null;

            if(rs.next()){
                user = new User();
                user.setId(rs.getString("id"));
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
            }

            if(user == null){
                throw new EmptyResultDataAccessException(1);
            }

            return user;

        }catch(SQLException e){
            e.printStackTrace();
            throw e;
        }finally {
            if(rs != null){
                try{
                    rs.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }

            if(ps != null){
                try{
                    ps.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }

            if(c != null){
                c.close();
            }
        }
    }



    public int getCount() throws SQLException{
        Connection c = dataSource.getConnection();

        PreparedStatement ps = c.prepareStatement("select count(*) from users");

        ResultSet rs = ps.executeQuery();
        rs.next();
        int count = rs.getInt(1);

        rs.close();
        ps.close();
        c.close();

        return count;
    }

    public void jdbcContextWithStatementStrategy(StatementStrategy stmt) throws SQLException{
        Connection c = null;
        PreparedStatement ps = null;

        try{
            c = dataSource.getConnection();

            ps = stmt.makePreparedStatement(c);

            ps.executeUpdate();

            // execute : 수행 결과를 Boolean 타입으로 반환
            // executeQuery : select 구문을 처리할 때 사용하며, 수행 결과를 ResultSet 타입으로 반환
            // executeUpdate : INSERT, UPDATE, DELETE 구문을 처리할 때 사용하며, 반영된 레코드 수를 int 타입으로 반환.
        }catch(SQLException e){
            e.printStackTrace();
            throw e;
        }finally {
            if(ps != null){ try{ ps.close();} catch (Exception e){e.printStackTrace();} }
            if(c != null){ try{ c.close(); }catch(Exception e){ e.printStackTrace();} }
        }
    }
}