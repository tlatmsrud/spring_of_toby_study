package org.example.user.dao;

import org.example.user.dao.strategy.StatementStrategy;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcContext {

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void workWithStatementStrategy(StatementStrategy stmt) throws SQLException {
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
