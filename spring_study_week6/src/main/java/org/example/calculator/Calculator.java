package org.example.calculator;

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
public class Calculator {
    public int calcSum(String path) throws IOException {

        return fileReaderTemplate(path, new BufferedReaderCallback() {
            @Override
            public Integer doSomeThingWithReader(BufferedReader br) throws IOException {
                Integer sum = 0;
                String line;

                while((line = br.readLine())!= null){
                    sum += Integer.valueOf(line);
                }
                return sum;
            }
        });
    }

    public int calcMultiply(String path) throws IOException{

        return fileReaderTemplate(path, new BufferedReaderCallback() {
            @Override
            public Integer doSomeThingWithReader(BufferedReader br) throws IOException {
                Integer multiply = 1;
                String line;

                while((line = br.readLine())!= null){
                    multiply *= Integer.valueOf(line);
                }
                return multiply;
            }
        });
    }

    public Integer fileReaderTemplate(String filePath, BufferedReaderCallback callback) throws IOException {
        BufferedReader br = null;

        try{
            br = new BufferedReader(new FileReader(filePath));
            int ret = callback.doSomeThingWithReader(br);
            return ret;
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if(br != null){
                try{br.close();}catch(Exception e){e.printStackTrace();}
            }
        }
    }
}
