package org.example.practice;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * title        :
 * author       : sim
 * date         : 2023-05-23
 * description  :
 */
public class Calculator2 {

    private BufferedReaderTemplate bufferedReaderTemplate;

    public void setBufferedReaderTemplate(BufferedReaderTemplate bufferedReaderTemplate){
        this.bufferedReaderTemplate = bufferedReaderTemplate;
    }

    public Integer calcSum(String path) throws IOException {

        return bufferedReaderTemplate.withCalculatorCallback(path, new CalculatorCallback() {
            @Override
            public Integer call(String line, Integer value) {
                return value + Integer.valueOf(line);
            }
        },0);
    }
    public Integer calcMultiply(String path) throws IOException {

        return bufferedReaderTemplate.withCalculatorCallback(path, (line, value) -> value * Integer.valueOf(line),1);
    }
}
