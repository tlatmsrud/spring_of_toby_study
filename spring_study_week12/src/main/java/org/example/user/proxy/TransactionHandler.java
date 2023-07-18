package org.example.user.proxy;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * title        : 트랜잭션 부가기능
 * author       : sim
 * date         : 2023-07-05
 * description  : 다이나믹 프록시에 사용할 트랜잭션 부가기능 클래스
 */
public class TransactionHandler implements InvocationHandler {
    private Object target;
    private PlatformTransactionManager transactionManager;
    private String pattern;

    public void setTarget(Object target){
        this.target = target;
    }

    public void setTransactionManager(PlatformTransactionManager transactionManager){
        this.transactionManager = transactionManager;
    }

    public void setPattern(String pattern){
        this.pattern = pattern;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        // 메서드 명이 pattern 으로 시작할 경우
        if(method.getName().startsWith(pattern)){
            return invokeInTransaction(method, args); // 트랜잭션 기능과 함께 메서드 실행
        }
        return method.invoke(target, args); // 트랜잭션 기능 없이 메서드 실행
    }

    public Object invokeInTransaction(Method method, Object[] args) throws Throwable {

        // 트랜잭션 생성
        TransactionStatus status = this.transactionManager
                .getTransaction(new DefaultTransactionDefinition());

        try{
            // 메서드 실행
            Object ret = method.invoke(target, args);

            // 트랜잭션 commit
            this.transactionManager.commit(status);
            return ret;
        } catch (InvocationTargetException e) {

            // 타겟 메서드 실행 중 예외 발생 시 트랜잭션 rollback
            this.transactionManager.rollback(status);
            throw e.getTargetException();
        }
    }
}
