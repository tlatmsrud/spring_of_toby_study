package org.example.proxy;


import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;


/**
 * title        :
 * author       : sim
 * date         : 2023-07-25
 * description  :
 */
public class TransactionAdvice implements MethodInterceptor {

    private PlatformTransactionManager transactionManager;

    public void setTransactionManager(PlatformTransactionManager transactionManager){
        this.transactionManager = transactionManager;
    }


    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        TransactionStatus status = transactionManager
                .getTransaction(new DefaultTransactionDefinition());

        try{
            Object ret = invocation.proceed(); //타겟 메서드 실행
            transactionManager.commit(status);
            return ret;
        } catch (RuntimeException e){
            transactionManager.rollback(status);
            throw e;
        }
    }
}
