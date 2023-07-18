package org.example.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * title        :
 * author       : sim
 * date         : 2023-07-18
 * description  :
 */
public class Main {

    public static void main(String[] args) throws Exception {

        getMethod();
    }

    public static void accessField() throws Exception {
        // JVM에 있는 클래스 정보 가져오기
        Class<?> class1 = Class.forName("org.example.reflection.Human");

        Constructor constructor = class1.getDeclaredConstructor(String.class);
        Object human = constructor.newInstance("승갱이");

        for(Field field : class1.getDeclaredFields()){
            System.out.println(field);
            field.setAccessible(true);
            System.out.println("value : "+ field.get(human));
        }
    }

    public static void updateField() throws Exception {
        // JVM에 있는 클래스 정보 가져오기
        Class<?> class1 = Class.forName("org.example.reflection.Human");

        Constructor constructor = class1.getDeclaredConstructor(String.class);
        Object human = constructor.newInstance("승갱이");

        for(Field field : class1.getDeclaredFields()){
            System.out.println(field);
            field.setAccessible(true);
            field.set(human, "변경된 승갱이");
            System.out.println("value : "+ field.get(human));
        }
    }


    public static void noSuchMethod() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        // JVM에 있는 클래스 정보 가져오기
        Class<?> class1 = Class.forName("org.example.reflection.Human");

        // 리플렉션을 통해 생성자 가져오기
        Constructor<?> constructor1 = class1.getConstructor(); // NoSuchMethodException !!
        Constructor<?> constructor2 = class1.getConstructor(String.class);

        // 가져온 생성자를 통해 객체 생성하기
        Object human1 = constructor1.newInstance();
        Object human2 = constructor2.newInstance("승갱이");
    }

    public static void getMethod() throws Exception{
        Class<?> class1 = Class.forName("org.example.reflection.Human");

        Constructor<?> constructor = class1.getConstructor(String.class);
        Object human = constructor.newInstance("승갱이");

        Method goRestRoomMethod = class1.getDeclaredMethod("goRestRoom");
        Method offPantsMethod = class1.getDeclaredMethod("offPants");
        Method doWorkMethod = class1.getDeclaredMethod("doWork");
        Method poopOutMethod = class1.getDeclaredMethod("poopOut");

        poopOutMethod.setAccessible(true);
        poopOutMethod.invoke(human);
        goRestRoomMethod.invoke(human);
        offPantsMethod.invoke(human);
        doWorkMethod.invoke(human);


    }
}
