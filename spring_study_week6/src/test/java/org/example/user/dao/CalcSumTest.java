package org.example.user.dao;

import org.example.calculator.Calculator;
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

    @Test
    public void sumOfNumbers() throws IOException {
        Calculator calculator = new Calculator();

        int sum = calculator.calcSum(getClass().getResource("/numbers.txt").getPath());

        assertThat(sum).isEqualTo(15);
    }
}
