package org.example.user.domain;

import org.example.user.enums.Level;

public class User {

    private String id;
    private String name;
    private String password;
    private Level level;
    private int login;
    private int recommend;

    public User(String id, String name, String password, Level level, int login, int recommend){
        this.id = id;
        this.name = name;
        this.password = password;
        this.level = level;
        this.login = login;
        this.recommend = recommend;
    }

    public User(){

    }

    public void upgradeLevel(){
        Level nextLevel = this.level.getNexeLevel();
        if(nextLevel == null){
            throw new IllegalStateException(this.level +"은 업그레이드가 불가능합니다.");
        }
        this.level = nextLevel;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public int getLogin() {
        return login;
    }

    public void setLogin(int login) {
        this.login = login;
    }

    public int getRecommend() {
        return recommend;
    }

    public void setRecommend(int recommend) {
        this.recommend = recommend;
    }
}
