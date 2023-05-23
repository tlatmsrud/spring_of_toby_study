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

        return lineReadTemplate(path, new LineCallback() {
            @Override
            public Integer doSomethingWithLing(String line, Integer value) {
                return value + Integer.valueOf(line);
            }
        }, 0);
    }

    public int calcMultiply(String path) throws IOException{

        return lineReadTemplate(path, new LineCallback() {
            @Override
            public Integer doSomethingWithLing(String line, Integer value) {
                return value * Integer.valueOf(line);
            }
        }, 1);
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

    public Integer lineReadTemplate(String filePath, LineCallback callback, int initVal) throws IOException {
        BufferedReader br = null;
        try{
            br = new BufferedReader(new FileReader(filePath));
            int res = initVal;
            String line;

            while((line = br.readLine()) != null){
                res = callback.doSomethingWithLing(line, res);
            }
            return res;
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
