package org.example.user.dao;

import org.example.user.dao.strategy.StatementStrategy;
import org.example.user.domain.User;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.sql.DataSource;
import java.sql.*;

public class UserDao {

    private JdbcContext jdbcContext;

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource){
        this.dataSource = dataSource;
        jdbcContext = new JdbcContext();
        jdbcContext.setDataSource(dataSource);
    }
    public void add(User user) throws SQLException {

        jdbcContext.workWithStatementStrategy(
                new StatementStrategy(){
                    @Override
                    public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
                        PreparedStatement ps = c.prepareStatement(
                                "insert into users(id, name, password) values(?,?,?)");

                        ps.setString(1, user.getId());
                        ps.setString(2, user.getName());
                        ps.setString(3, user.getPassword());
                        return ps;
                    }
                });

    }

    public void deleteAll() throws SQLException{
        jdbcContext.workWithStatementStrategy(new StatementStrategy() {
            @Override
            public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
                return c.prepareStatement("delete from users");
            }
        });
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

}