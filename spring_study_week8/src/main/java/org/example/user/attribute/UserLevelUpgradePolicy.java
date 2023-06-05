package org.example.user.attribute;

import org.example.user.domain.User;


public interface UserLevelUpgradePolicy {
    boolean canUpgradeLevel(User user);

    void upgradeLevel(User user);
}
