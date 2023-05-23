package org.example.user.dao;

import org.example.user.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.*;

public class UserDao {

    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    public void add(final User user)  {
        jdbcTemplate.update("insert into users(id, name, password) values(?,?,?)",
                user.getId(), user.getName(),user.getPassword());
    }

    public void deleteAll() throws SQLException{
        jdbcTemplate.update("delete from users");
    }


    public User get(String id) throws SQLException{
        return jdbcTemplate.queryForObject("select * from users where id = ?", new Object[]{id},
                new RowMapper<User>() {
                    @Override
                    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                        User user = new User();
                        user.setId(rs.getString("id"));
                        user.setName(rs.getString("name"));
                        user.setPassword(rs.getString("password"));
                        return user;
                    }
                });
    }



    public int getCount() throws SQLException{
        return jdbcTemplate.queryForInt("select count(*) from users");
    }
}