package org.example.user.domain;

import org.example.user.enums.Level;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserTest {

    private User user;

    @BeforeEach
    void setUp(){
        user = new User();
    }

    @Test
    @DisplayName("업그레이드 가능 유저에 대한 레벨 업그레이드")
    public void upgradeLevelWithAble(){
        Level[] levels = Level.values();

        for(Level level : levels){
            if(level.getNexeLevel() == null) continue;
            user.setLevel(level);
            user.upgradeLevel();
            assertThat(user.getLevel()).isEqualTo(level.getNexeLevel());
        }
    }

    @Test
    @DisplayName("업그레이드 불가 유저에 대한 레벨 업그레이드")
    public void upgradeLevelWithUnAble(){
        user.setLevel(Level.GOLD);
        assertThatThrownBy(() -> user.upgradeLevel()).isInstanceOf(IllegalStateException.class);
    }

}