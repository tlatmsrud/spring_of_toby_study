package org.example.user.service;

import org.example.user.domain.User;

public interface UserService {

    void upgradeLevels();
    void add(User user);
}
