package org.example;

import jdk.nashorn.internal.runtime.regexp.joni.exception.InternalException;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;


public class ExceptionCase {

    public static void main(String[] args) throws IOException {

        test();
    }

    public static void test() throws IOException {

        BufferedReader br = null;

        ClassPathResource resource = new ClassPathResource("data.json");
        br = new BufferedReader(new InputStreamReader(resource.getInputStream()));

    }

    public static void exceptionRecovery(){

        int maxretry = 5;

        while(maxretry -- > 0){
            try{
                //예외 발생 가능성이 있는 코드
            }catch(Exception e){
                //로그 출력 및 정해진 시간만큼 대기
            }finally {
                //리소스 반납 및 정리작업
            }
        }

        throw new InternalException("에러가 발생하였습니다.");
    }
}
