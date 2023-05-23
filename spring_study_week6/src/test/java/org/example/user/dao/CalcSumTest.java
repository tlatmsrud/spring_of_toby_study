package org.example.user.dao;

import org.example.calculator.Calculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * title        :
 * author       : sim
 * date         : 2023-05-23
 * description  :
 */
public class CalcSumTest {

    Calculator calculator;
    String filePath;

    @BeforeEach
    void setUp(){
        this.calculator = new Calculator();
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

    @Test
    public void concatenateOfStrings() throws IOException {

        String result = calculator.concatenateStrings(filePath);

        assertThat(result).isEqualTo("12345");
    }
}

