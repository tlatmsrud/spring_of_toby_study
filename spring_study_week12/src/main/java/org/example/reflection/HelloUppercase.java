package org.example.reflection;

/**
 * title        :
 * author       : sim
 * date         : 2023-07-05
 * description  :
 */
public class HelloUppercase implements Hello{

    private final Hello hello;
    public HelloUppercase(Hello hello){
        this.hello = hello;
    }

    @Override
    public String sayHello(String name) {
        return hello.sayHello(name).toUpperCase();
    }

    @Override
    public String sayHi(String name) {
        return hello.sayHi(name).toUpperCase();
    }

    @Override
    public String sayThankYou(String name) {
        return hello.sayThankYou(name).toUpperCase();
    }
}
