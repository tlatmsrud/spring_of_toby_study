package org.example.user.dao;

import org.example.user.domain.User;
import org.example.user.enums.Level;

import java.util.List;

/**
 * title        :
 * author       : sim
 * date         : 2023-06-19
 * description  :
 */
public class StubUserDao implements IUserDao{


    @Override
    public void add(User user) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public User get(String id) {

        if("100".equals(id)){
            throw new RuntimeException("삭제된 ID입니다.");
        }
        return new User("1", "테스터","비밀번호", Level.BASIC,0
                ,0,"test@naver.com");
    }

    @Override
    public int getCount() {
        return 100;
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public int update(User user) {
        return 0;
    }
}
