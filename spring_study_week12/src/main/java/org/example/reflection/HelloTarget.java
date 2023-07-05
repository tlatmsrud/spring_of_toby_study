package org.example.reflection;

/**
 * title        :
 * author       : sim
 * date         : 2023-07-05
 * description  :
 */
public class HelloTarget implements Hello{

    @Override
    public String sayHello(String name) {
        return "Hello "+name;
    }

    @Override
    public String sayHi(String name) {
        return "Hi "+name;
    }

    @Override
    public String sayThankYou(String name) {
        return "Thank You "+name;
    }
}
