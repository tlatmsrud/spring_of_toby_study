package org.example.reflection;

import org.example.reflection.di.SSKAutowired;

/**
 * title        :
 * author       : sim
 * date         : 2023-07-18
 * description  :
 */

public class Human {

    private String name;

    public Human(String name){
        this.name = name;
    }

    private Human(){

    }

    public void goRestRoom(){
        System.out.println(name +"이 화장실로 갑니다.");
    }

    public void offPants(){
        System.out.println(name +"이 바지를 내립니다.");
    }

    public void doWork(){
        System.out.println(name + "이 볼일을 봅니다.");
        poopOut();
    }

    private void poopOut(){
        System.out.println("똥이 나왔습니다.");
    }
}
