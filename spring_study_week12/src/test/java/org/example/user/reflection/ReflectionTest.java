package org.example.user.reflection;

import org.example.reflection.Hello;
import org.example.reflection.HelloTarget;
import org.example.reflection.HelloUppercase;
import org.example.reflection.UppercaseHandler;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * title        :
 * author       : sim
 * date         : 2023-07-05
 * description  :
 */
public class ReflectionTest {

    @Test
    public void invokeMethod() throws Exception{
        String name = "Spring";

        assertThat(name.length()).isEqualTo(6);

        Method lengthMethod = String.class.getMethod("length");
        assertThat((Integer)lengthMethod.invoke(name)).isEqualTo(6);

        assertThat(name.charAt(0)).isEqualTo('S');
        Method charAtMethod = String.class.getMethod("charAt", int.class); // method name, parameterType ...
        assertThat((Character) charAtMethod.invoke(name,0)).isEqualTo('S');
    }

    @Test
    public void simpleProxy(){
        Hello hello = new HelloTarget(); // 타깃은 인터페이스를 통해 접근
        assertThat(hello.sayHello("Sim")).isEqualTo("Hello Sim");
        assertThat(hello.sayHi("Sim")).isEqualTo("Hi Sim");
        assertThat(hello.sayThankYou("Sim")).isEqualTo("Thank You Sim");

        Hello proxyHello = new HelloUppercase(hello);
        assertThat(proxyHello.sayHello("Sim")).isEqualTo("HELLO SIM");
        assertThat(proxyHello.sayHi("Sim")).isEqualTo("HI SIM");
        assertThat(proxyHello.sayThankYou("Sim")).isEqualTo("THANK YOU SIM");
    }

    @Test
    public void dynamicProxy(){
        Hello proxyHello = (Hello) Proxy.newProxyInstance(
                getClass().getClassLoader(), // 다이나믹 프록시를 정의하는 클래스 로더
                new Class[] {Hello.class}, // 다이나믹 프록시가 구현해야할 인터페이스
                new UppercaseHandler(new HelloTarget())); // 부가기능 및 위임 코드를 담는 InvocationHandler 구현 클래스

        assertThat(proxyHello.sayThankYou("Sim")).isEqualTo("THANK YOU SIM");
    }
}
