package org.example.user.dao;

import org.example.user.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class UserDaoJdbc implements IUserDao{

    private JdbcTemplate jdbcTemplate;

    private RowMapper<User> userMapper = new RowMapper<User>() {

        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getString("id"));
            user.setName(rs.getString("name"));
            user.setPassword(rs.getString("password"));
            return user;
        }
    };

    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    public void add(final User user)  {
        jdbcTemplate.update("insert into users(id, name, password) values(?,?,?)",
                user.getId(), user.getName(),user.getPassword());
    }

    public void deleteAll(){
        jdbcTemplate.update("delete from users");
    }


    public User get(String id){
        return jdbcTemplate.queryForObject("select * from users where id = ?", new Object[]{id},
                userMapper);
    }

    public int getCount(){
        return jdbcTemplate.queryForInt("select count(*) from users");
    }

    public List<User> getAll() {
        return jdbcTemplate.query("select * from users order by id",
                userMapper);
    }
}