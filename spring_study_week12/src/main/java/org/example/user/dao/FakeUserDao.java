package org.example.user.dao;

import org.example.user.domain.User;
import org.example.user.enums.Level;

import java.util.ArrayList;
import java.util.List;

/**
 * title        :
 * author       : sim
 * date         : 2023-06-19
 * description  :
 */
public class FakeUserDao implements IUserDao{

    private final List<User> list = new ArrayList<>();

    @Override
    public void add(User user) {
        list.add(user);
    }

    @Override
    public void deleteAll() {
        list.clear();
    }

    @Override
    public User get(String id) {

        return list.stream()
                .filter(user -> id.equals(user.getId()))
                .findFirst()
                .get();
    }

    @Override
    public int getCount() {
        return list.size();
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
