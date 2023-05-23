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
public class BufferedReaderTemplate {

    public Integer withCalculatorCallback(String path, CalculatorCallback callback, Integer initVal) throws IOException {
        BufferedReader br = null ;
        try{
            br = new BufferedReader(new FileReader(path));
            String line;

            Integer res = initVal;
            while((line = br.readLine()) != null) {
                res = callback.call(line, res);
            }

            return res;
        }catch(FileNotFoundException e){
            e.printStackTrace();
            throw e;
        }finally {
            if(br != null){
                br.close();
            }
        }

    }
}
