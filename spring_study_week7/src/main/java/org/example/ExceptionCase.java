package org.example;

import com.mysql.cj.exceptions.MysqlErrorNumbers;
import jdk.nashorn.internal.runtime.regexp.joni.exception.InternalException;
import org.example.user.domain.User;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.SQLException;


public class ExceptionCase {

    public static void main(String[] args) throws IOException {

        test();
    }

    public static void test() throws IOException {

        BufferedReader br = null;

        ClassPathResource resource = new ClassPathResource("data.json");
        br = new BufferedReader(new InputStreamReader(resource.getInputStream()));

    }

    public static void exceptionRecovery(){

        int maxretry = 5;

        while(maxretry -- > 0){
            try{
                //예외 발생 가능성이 있는 코드
            }catch(Exception e){
                //로그 출력 및 정해진 시간만큼 대기
            }finally {
                //리소스 반납 및 정리작업
            }
        }

        throw new InternalException("에러가 발생하였습니다.");
    }

    public static void exceptionEvasion() throws SQLException{

        // #1. try/catch와 throws를 통한 예외회피
        try{
            /// JDBC API
            throw new SQLException();
        }catch(SQLException e){
            throw e;
        }

        // #2. throws를 통한 예외회피

        // JDBC API

    }

    public static void exceptionTransform() {
        try{
            throw new SQLException();
        }catch (SQLException e){
            if(e.getErrorCode() == MysqlErrorNumbers.ER_DUP_ENTRY){
                //throw new DuplicateUserIdException();
                throw new DuplicateUserIdException(e);
                //throw new DuplicateUserIdException().initCause(e);
            }
        }
    }

    public static void join(User user) throws DuplicateUserIdException{

        try{
            // 1. 회원가입 로직 ...
            // 2. ID, PW 정보 DB INSERT 시 ID 중복(무결정 제약조건 위배)되어 SQLException 발생
            throw new SQLException();
        }catch(SQLException e){
            if(e.getErrorCode() == MysqlErrorNumbers.ER_DUP_ENTRY){
                throw new DuplicateUserIdException(e);  // 3. DuplicateUserIdException 예외 throw
            }
        }
    }

    public static void requestJoin(User user){

        try{
            join(user);
        }catch(DuplicateUserIdException e){
            // 예외 처리
        }
    }

}
