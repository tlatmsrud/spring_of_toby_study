package org.example.user.dao.templateMethod;

import org.example.user.domain.User;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class UserDaoTemplateMethod {

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource){
        this.dataSource = dataSource;
    }

    abstract PreparedStatement makeStatement(Connection c) throws SQLException;

    public void deleteAll() throws SQLException{

        Connection c = null;
        PreparedStatement ps = null;

        try{
            c = dataSource.getConnection();

            ps = makeStatement(c);

            ps.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
            throw e;
        }finally {
            if(ps != null){
                try{
                    ps.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }

            if(c != null){
                try{
                    c.close();
                }catch(Exception e){
                    e.printStackTrace();
                }

            }

        }

    }

    public void add() throws SQLException{

        Connection c = null;
        PreparedStatement ps = null;

        try{
            c = dataSource.getConnection();

            ps = makeStatement(c);

            ps.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
            throw e;
        }finally {
            if(ps != null){
                try{
                    ps.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }

            if(c != null){
                try{
                    c.close();
                }catch(Exception e){
                    e.printStackTrace();
                }

            }

        }

    }

}