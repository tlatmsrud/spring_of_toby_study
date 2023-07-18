package org.example.reflection.di;

import java.lang.reflect.Constructor;
import java.util.Arrays;

/**
 * title        :
 * author       : sim
 * date         : 2023-07-18
 * description  :
 */
public class CustomApplicationContext {

    /**
     * 클래스의 멤버필드 중 SSKAutowired가 붙어있을 경우 의존성 주입
     * @param clazz - 스캔 클래스
     * @return - 의존주입이 완료된 스캔 클래스
     * @throws Exception
     */
    public static <T> T getInstance(Class<T> clazz) throws Exception{

        T instance = createInstance(clazz);
        Arrays.stream(clazz.getDeclaredFields()).forEach(field -> {
            if(field.getAnnotation(SSKAutowired.class) != null){ // SSKAutowired가 붙은 멤버필드일 경우
                try {
                    Object fieldInstance = createInstance(field.getType()); // 멤버필드에 대한 객체 생성
                    field.setAccessible(true);
                    field.set(instance, fieldInstance); // 생성된 객체를 instance에 셋팅 (DI)
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        return instance;
    }

    /**
     * 리플렉션 기본 생성자를 통해 객체 생성
     * @param clazz - 클래스 타입
     * @return 클래스 객체
     * @throws Exception
     */
    private static <T> T createInstance(Class<T> clazz) throws Exception{
        Constructor<T> constructor = clazz.getDeclaredConstructor(); // 리플렉션을 통해 클래스의 기본생성자 정보 조회
        constructor.setAccessible(true);
        return constructor.newInstance(); // 객체 생성
    }
}
