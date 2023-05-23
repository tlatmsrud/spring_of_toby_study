package org.example.user.dao;

import org.example.calculator.Calculator;
import org.example.practice.Calculator2;
import org.example.practice.ObjFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * title        :
 * author       : sim
 * date         : 2023-05-23
 * description  :
 */
public class PracticeCalculatorTest {

    private Calculator2 calculator;
    private String filePath;

    @BeforeEach
    void setUp(){
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ObjFactory.class);
        this.calculator = applicationContext.getBean("calculator2", Calculator2.class);
        this.filePath = getClass().getResource("/numbers.txt").getPath();
    }
    @Test
    public void sumOfNumbers() throws IOException {

        int sum = calculator.calcSum(filePath);

        assertThat(sum).isEqualTo(15);
    }

    @Test
    public void multiplyOfNumbers() throws IOException {

        int sum = calculator.calcMultiply(filePath);

        assertThat(sum).isEqualTo(120);
    }
}
