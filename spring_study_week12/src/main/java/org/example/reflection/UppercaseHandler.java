package org.example.reflection;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * title        :
 * author       : sim
 * date         : 2023-07-05
 * description  :
 */
public class UppercaseHandler implements InvocationHandler {

    private Object target;

    public UppercaseHandler(Object target){
        this.target = target;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object ret = method.invoke(target, args); // invoke를 통해 target 오브젝트의 method 실행

        if(ret instanceof String && method.getName().startsWith("say")){ // 문자열 타입이거나 get으로 시작하는 메서드일 경우
            return ((String)ret).toUpperCase();
        }
        return ret;
    }
}
