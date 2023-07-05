package org.example.user.dao;


import org.example.user.domain.User;

import java.util.List;

public interface IUserDao {

    public void add(User user) ;
    public void deleteAll();
    public User get(String id);
    public int getCount();
    public List<User> getAll();
    public int update(User user);
}
